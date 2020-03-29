package org.tiramisu.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface DataClient<PARAM, RESULT: Any> {

    /**
     * 异步请求
     */
    fun sendDataRequest(param: PARAM, callback: DataCallback<PARAM, RESULT>): Disposable

    /**
     * 同步请求
     */
    fun sendDataRequest(param: PARAM): DataResult<RESULT>

    /**
     * 协程请求
     */
    suspend fun sendCoroutineRequest(
        param: PARAM,
        scope: CoroutineContext = Dispatchers.IO
    ): DataResult<RESULT> = withContext(scope) {
        sendDataRequest(param)
    }
}