package org.tiramisu.network.service

import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import java.io.Reader

class FuelHttpClient : HttpClient {

    override fun <T: Any> sendHttpRequest(
        url: String,
        method: HttpMethod,
        clazz: Class<T>,
        params: Map<String, String>,
        headers: Map<String, String>?,
        callback: HttpCallback<T>?
    ) {
        buildRequest(url, method, params, headers)
            .response(FuelResponseDeserializable(clazz), FuelResponseHandler(callback))
            .join()
    }

    private fun buildRequest(
        url: String, method: HttpMethod,
        params: Map<String, String>,
        headers: Map<String, String>?
    ): Request {
        val parameters = params.entries.map { it.key to it.value }
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

    class FuelResponseHandler<T: Any>(
        private val callback: HttpCallback<T>?
    ) : ResponseResultHandler<T> {
        override fun invoke(request: Request, response: Response, result: Result<T, FuelError>) {
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    callback?.onError(response.statusCode, ex.message)
                }
                is Result.Success -> {
                    val data = result.get()
                    callback?.onSuccess(data)
                }
            }
        }

    }
}