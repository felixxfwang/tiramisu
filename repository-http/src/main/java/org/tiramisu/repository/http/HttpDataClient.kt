package org.tiramisu.repository.http

import org.tiramisu.http.*
import org.tiramisu.repository.DataCallback
import org.tiramisu.repository.DataClient
import org.tiramisu.repository.Disposable

open class HttpDataClient<PARAM: HttpParam, RESULT: Any>(
    baseUrl: String,
    protected val path: String,
    protected val rspClass: Class<RESULT>
) : DataClient<PARAM, RESULT> {

    protected val http = TiramisuHttp().baseUrl(baseUrl)

    override fun sendDataRequest(
        param: PARAM,
        callback: DataCallback<PARAM, RESULT>
    ): Disposable {
        return HttpDisposable(
            http.client.sendHttpRequest(http.wrapUrl(path), HttpMethod.GET, rspClass, param, callback = HttpDataCallback(callback))
        )
    }

    class HttpDataCallback<P: HttpParam, R>(private val callback: DataCallback<P, R>): HttpCallback<P, R> {

        override fun onSuccess(param: P, data: R) {
            callback.onSuccess(param, data)
        }

        override fun onError(param: P, error: HttpException) {
            callback.onError(param, error.code, error.message)
        }

    }
}