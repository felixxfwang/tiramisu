package org.tiramisu.repository.http

import org.tiramisu.http.HttpCallback
import org.tiramisu.http.HttpMethod
import org.tiramisu.http.HttpParam
import org.tiramisu.http.TiramisuHttp
import org.tiramisu.repository.DataCallback
import org.tiramisu.repository.DataClient
import org.tiramisu.repository.Disposable

class HttpDataClient<PARAM: HttpParam, RESULT: Any>(
    baseUrl: String,
    private val path: String,
    private val rspClass: Class<RESULT>
) : DataClient<PARAM, RESULT> {

    private val http = TiramisuHttp().baseUrl(baseUrl)

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

        override fun onError(param: P, errorCode: Int, errorMessage: String?) {
            callback.onError(param, errorCode, errorMessage)
        }

    }
}