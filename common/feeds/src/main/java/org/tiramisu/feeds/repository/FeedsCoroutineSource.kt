package org.tiramisu.feeds.repository

import org.tiramisu.log.TLog
import org.tiramisu.repository.DataException
import org.tiramisu.repository.DataResult
import org.tiramisu.repository.coroutine.AbstractCoroutineDataSource
import org.tiramisu.repository.transform
import java.util.concurrent.atomic.AtomicBoolean

abstract class FeedsCoroutineSource<P: FeedReqParameter, D, REQ, RSP: Any, KEY> : AbstractCoroutineDataSource<P, D, REQ, RSP>(), PagingCoroutineDataSource<P, D> {

    companion object {
        private const val TAG = "FeedsCoroutineSource"
    }

    private val isLastPage = AtomicBoolean(false)
    protected var nextKey: KEY? = null

    private suspend fun sendDataRequest(param: P, isLoadInitial: Boolean): DataResult<D> {
        try {
            val intercepted = onRequestPreProcess(param, isLoadInitial)
            return if (intercepted == null) {
                val req = getRequest(param, isLoadInitial)
                TLog.i(TAG, "request: $req")
                val result = client.sendRequest(req)
                if (result.isSuccess()) {
                    val data = result.get()
                    val isLast = isLastPage(data)
                    isLastPage.set(isLast)
                    nextKey = getNextKeyFromRsp(req, data)
                }
                result.transform { getResponse(param, it, isLoadInitial) }.also {
                    if (it.isSuccess()) {
                        onResponsePostProcess(
                            param,
                            result.get(),
                            it.get(),
                            isLoadInitial,
                            isLastPage.get()
                        )
                    }
                }
            } else {
                intercepted
            }
        } catch (e: Exception) {
            TLog.e(TAG, "sendDataRequest failed", e)
            return DataResult.error(DataException(-1, e.message, e))
        }
    }

    protected open fun onRequestPreProcess(param: P, isLoadInitial: Boolean): DataResult<D>? = null
    protected open fun onResponsePostProcess(param: P, rsp: RSP, data: D, isLoadInitial: Boolean, isLastPage: Boolean) {}

    override fun isLastPage(): Boolean = isLastPage.get()

    override suspend fun loadData(param: P): DataResult<D> = loadInitial(param)

    override suspend fun loadInitial(param: P): DataResult<D> {
        return sendDataRequest(param, true)
    }

    override suspend fun loadAfter(param: P): DataResult<D> {
        return sendDataRequest(param, false)
    }

    protected abstract fun getRequest(param: P, isLoadInitial: Boolean): REQ

    protected abstract fun getResponse(param: P, rsp: RSP, isLoadInitial: Boolean): D

    protected abstract fun getNextKeyFromRsp(req: REQ, rsp: RSP): KEY

    protected abstract fun isLastPage(rsp: RSP): Boolean
}