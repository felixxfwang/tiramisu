package org.tiramisu.repository.http

import kotlinx.coroutines.runBlocking
import org.tiramisu.http.HttpParam
import org.tiramisu.http.HttpResult
import org.tiramisu.http.coroutine.coroutineGet
import org.tiramisu.repository.DataCallback
import org.tiramisu.repository.Disposable

class HttpCoroutineDataClient <PARAM: HttpParam, RESULT: Any>(
    baseUrl: String,
    path: String,
    rspClass: Class<RESULT>
) : HttpDataClient<PARAM, RESULT>(baseUrl, path, rspClass) {

//    override fun sendDataRequest(param: PARAM, callback: DataCallback<PARAM, RESULT>): Disposable {
//        runBlocking {
//            val result: HttpResult<RESULT> = http.coroutineGet(http.wrapUrl(path), param)
//        }
//    }
}