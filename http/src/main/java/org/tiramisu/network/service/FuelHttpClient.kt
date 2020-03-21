package org.tiramisu.network.service

import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import java.io.Reader

class FuelHttpClient : HttpClient {

    override fun <P : HttpParam, T : Any> sendHttpRequest(
        url: String,
        method: HttpMethod,
        clazz: Class<T>,
        params: P,
        headers: Map<String, Any>?,
        callback: HttpCallback<P, T>?
    ) {
        buildRequest(url, method, params, headers)
            .response(FuelResponseDeserializable(clazz), FuelResponseHandler(params, callback))
            .join()
    }

    private fun <P: HttpParam> buildRequest(
        url: String, method: HttpMethod,
        params: P, headers: Map<String, Any>?
    ): Request {
        val parameters = params.toMap().entries.map { it.key to it.value }
        val request = when (method) {
            HttpMethod.GET -> url.httpGet(parameters)
            HttpMethod.POST -> url.httpGet(parameters)
            HttpMethod.PUT -> url.httpPut(parameters)
            HttpMethod.DELETE -> url.httpDelete(parameters)
        }
        headers?.let { request.header(it) }
        return request
    }

    class FuelResponseDeserializable<T: Any>(
        private val clazz: Class<T>
    ) : ResponseDeserializable<T> {
        override fun deserialize(reader: Reader): T? {
            return Gson().fromJson<T>(reader, clazz)
        }
    }

    class FuelResponseHandler<P: HttpParam, T: Any>(
        private val param: P,
        private val callback: HttpCallback<P, T>?
    ) : ResponseResultHandler<T> {
        override fun invoke(request: Request, response: Response, result: Result<T, FuelError>) {
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    callback?.onError(param, response.statusCode, ex.message)
                }
                is Result.Success -> {
                    val data = result.get()
                    callback?.onSuccess(param, data)
                }
            }
        }

    }
}