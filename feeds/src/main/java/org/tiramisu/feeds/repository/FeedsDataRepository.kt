package org.tiramisu.feeds.repository

import org.tiramisu.log.TLog
import org.tiramisu.repository.BaseDataRepository
import org.tiramisu.repository.DataCallback
import org.tiramisu.repository.LoadCallback
import java.util.concurrent.atomic.AtomicBoolean

abstract class FeedsDataRepository<P: FeedReqParameter, D, REQ, RSP, KEY>()
    : BaseDataRepository<P, D, REQ, RSP>(),
    PagingDataRepository<P, D> {

    companion object {
        private const val TAG = "FeedsDataRepository"
    }

    private val isLoadingInitial = AtomicBoolean(false)
    private val isLoadingAfter = AtomicBoolean(false)
    private var isLastPage = AtomicBoolean(false)
    protected var nextKey: KEY? = null

    override fun loadData(param: P, callback: LoadInitialCallback<P, D>?) = loadInitial(param, callback)

    override fun loadInitial(param: P, callback: LoadInitialCallback<P, D>?) {
        callback?.onLoadDataStart(param)
        sendDataRequest(param, true, callback)
    }

    override fun loadAfter(param: P, callback: LoadMoreCallback<P, D>?) {
        callback?.onLoadMoreStart(param)
        sendDataRequest(param, false, callback)
    }

    override fun cancel() {
        super.cancel()
        isLoadingInitial.set(false)
        isLoadingAfter.set(false)
    }

    override fun isLoading(): Boolean = isLoadingInitial() || isLoadingAfter()

    override fun isLoadingInitial(): Boolean = isLoadingInitial.get()

    override fun isLoadingAfter(): Boolean = isLoadingAfter.get()

    override fun isLastPage(): Boolean = isLastPage.get()

    private fun sendDataRequest(param: P, isLoadInitial: Boolean, callback: LoadCallback<P, D>?) {
        setLoadingState(isLoadInitial, true)
        val intercepted = onRequestPreProcess(param, isLoadInitial, callback)
        if (!intercepted) {
            val req = getRequest(param, isLoadInitial)
            TLog.i(TAG, "request: $req")
            request = client.sendDataRequest(req, object : DataCallback<REQ, RSP> {
                override fun onSuccess(req: REQ, data: RSP) {
                    // 解析回包数据
                    val rsp = getDataListFromRsp(param, data, isLoadInitial)
                    // 数据后处理
                    val isLast = isLastPage(data)
                    isLastPage.set(isLast)
                    nextKey = getNextKeyFromRsp(req, data)
                    onResponsePostProcess(param, data, rsp, isLoadInitial, isLast)

                    TLog.i(TAG, "onSuccess: $data")

                    // 回调给业务
                    onLoadSuccess(param, isLoadInitial, callback, rsp, isLast)
                    onLoadComplete(param, isLoadInitial, callback)
                    setLoadingState(isLoadInitial, false)
                }

                override fun onError(req: REQ, errorCode: Int, errorMessage: String?) {
                    TLog.e(TAG, "onError: errCode=$errorCode, errMsg: $errorMessage")
                    onLoadFailed(param, isLoadInitial, callback, errorCode, errorMessage)
                    onLoadComplete(param, isLoadInitial, callback)
                    setLoadingState(isLoadInitial, false)
                }

            })
        }
    }

    private fun setLoadingState(isLoadInitial: Boolean, isLoading: Boolean) {
        if (isLoadInitial) {
            isLoadingInitial.set(isLoading)
        } else {
            isLoadingAfter.set(isLoading)
        }
    }

    private fun onLoadSuccess(param: P, isLoadInitial: Boolean, callback: LoadCallback<P, D>?, data: D, isLastPage: Boolean) {
        if (isLoadInitial) {
            (callback as? LoadInitialCallback)?.onLoadDataSuccess(param, data)
        } else {
            (callback as? LoadMoreCallback)?.onLoadMoreSuccess(param, data, isLastPage)
        }
    }

    private fun onLoadFailed(param: P, isLoadInitial: Boolean, callback: LoadCallback<P, D>?, code: Int, errorMessage: String?) {
        if (isLoadInitial) {
            (callback as? LoadInitialCallback)?.onLoadDataFailed(param, code, errorMessage)
        } else {
            (callback as? LoadMoreCallback)?.onLoadMoreFailed(param, code, errorMessage)
        }
    }

    private fun onLoadComplete(param: P, isLoadInitial: Boolean, callback: LoadCallback<P, D>?) {
        if (isLoadInitial) {
            (callback as? LoadInitialCallback)?.onLoadDataComplete(param)
        } else {
            (callback as? LoadMoreCallback)?.onLoadMoreComplete(param)
        }
    }

    protected abstract fun getRequest(param: P, isLoadInitial: Boolean): REQ

    protected abstract fun getDataListFromRsp(param: P, rsp: RSP, isLoadInitial: Boolean): D

    protected abstract fun getNextKeyFromRsp(req: REQ, rsp: RSP): KEY

    protected abstract fun isLastPage(rsp: RSP): Boolean

    protected open fun onRequestPreProcess(param: P, isLoadInitial: Boolean, callback: LoadCallback<P, D>?): Boolean = false

    protected open fun onResponsePostProcess(param: P, rsp: RSP, data: D, isLoadInitial: Boolean, isLastPage: Boolean) {}
}
