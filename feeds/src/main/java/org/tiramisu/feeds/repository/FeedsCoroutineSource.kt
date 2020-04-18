package org.tiramisu.feeds.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.tiramisu.log.TLog
import org.tiramisu.repository.LoadCallback
import org.tiramisu.repository.Result

abstract class FeedsCoroutineSource<P: FeedReqParameter, D, REQ, RSP: Any, KEY>(
    private val scope: CoroutineScope
) : FeedsDataSource<P, D, REQ, RSP, KEY>() {
    companion object {
        private const val TAG = "FeedsCoroutineRepository"
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
                    onLoadSuccess(param, data, isLoadInitial, req, callback)
                } else {
                    val error = (result as Result.Failure).getException()
                    onLoadFailed(error, param, isLoadInitial, callback)
                }
            }
        }
    }
}