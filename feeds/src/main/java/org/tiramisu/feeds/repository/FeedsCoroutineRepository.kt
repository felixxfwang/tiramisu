package org.tiramisu.feeds.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.tiramisu.log.TLog
import org.tiramisu.repository.LoadCallback
import org.tiramisu.repository.Result

abstract class FeedsCoroutineRepository<P: FeedReqParameter, D, REQ, RSP: Any, KEY> : FeedsDataRepository<P, D, REQ, RSP, KEY>() {
    companion object {
        private const val TAG = "FeedsCoroutineRepository"
    }

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun cancel() {
        scope.cancel()
    }

    override fun sendDataRequest(param: P, isLoadInitial: Boolean, callback: LoadCallback<P, D>?) {
        setLoadingState(isLoadInitial, true)
        val intercepted = onRequestPreProcess(param, isLoadInitial, callback)
        if (!intercepted) {
            val req = getRequest(param, isLoadInitial)
            TLog.i(TAG, "request: $req")
            scope.launch {
                val result = client.sendCoroutineRequest(req)
                if (result.isSuccess()) {
                    val data = result.get()
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
                } else {
                    val error = (result as Result.Failure).getException()
                    TLog.e(TAG, "onError: errCode=${error.code}, errMsg: ${error.message}")
                    onLoadFailed(param, isLoadInitial, callback, error.code, error.message)
                    onLoadComplete(param, isLoadInitial, callback)
                    setLoadingState(isLoadInitial, false)
                }
            }
        }
    }
}