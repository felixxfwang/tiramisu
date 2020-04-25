package org.tiramisu.repository.http

import kotlinx.coroutines.withContext
import org.tiramisu.http.HttpMethod
import org.tiramisu.http.HttpParam
import org.tiramisu.http.Result
import org.tiramisu.http.TiramisuHttp
import org.tiramisu.repository.DataException
import org.tiramisu.repository.DataResult
import org.tiramisu.repository.coroutine.CoroutineDataClient
import kotlin.coroutines.CoroutineContext

class HttpCoroutineDataClient <PARAM: HttpParam, RESULT: Any>(
    baseUrl: String,
    private val path: String,
    private val rspClass: Class<RESULT>
) : CoroutineDataClient<PARAM, RESULT> {

    private val http = TiramisuHttp().baseUrl(baseUrl)

    override suspend fun sendRequest(param: PARAM, scope: CoroutineContext): DataResult<RESULT> {
        return withContext(scope) { doSendRequest(param) }
    }

    private fun doSendRequest(param: PARAM): DataResult<RESULT> {
        return when (val result = http.client.sendHttpRequest(http.wrapUrl(path), HttpMethod.GET, rspClass, param)) {
            is Result.Success -> DataResult.success(result.get())
            is Result.Failure -> DataResult.error(DataException(result.error.code, result.error.message, result.error.cause))
        }
    }
}