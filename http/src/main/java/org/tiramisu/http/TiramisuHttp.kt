package org.tiramisu.http

import org.tiramisu.http.fuel.FuelHttpClient

class TiramisuHttp {

    private var baseUrl: String = ""

    fun baseUrl(url: String): TiramisuHttp {
        this.baseUrl = url
        return this
    }

    fun wrapUrl(url: String): String {
        if (url.startsWith("http")) return url
        return baseUrl + url
    }

    val client: HttpClient = FuelHttpClient()

    inline fun <P: HttpParam, reified T : Any> get(
        url: String, params: P,
        headers: Map<String, Any>? = null,
        callback: HttpCallback<P, T>? = null
    ): HttpCancellable {
        return client.sendHttpRequest(wrapUrl(url), HttpMethod.GET, T::class.java, params, headers, callback)
    }

    inline fun <P: HttpParam, reified T : Any> post(
        url: String, params: P,
        headers: Map<String, Any>? = null,
        callback: HttpCallback<P, T>? = null
    ) : HttpCancellable {
        return client.sendHttpRequest(wrapUrl(url), HttpMethod.POST, T::class.java, params, headers, callback)
    }

    inline fun <P: HttpParam, reified T : Any> put(
        url: String, params: P,
        headers: Map<String, Any>? = null,
        callback: HttpCallback<P, T>? = null
    ) : HttpCancellable {
        return client.sendHttpRequest(wrapUrl(url), HttpMethod.PUT, T::class.java, params, headers, callback)
    }

    inline fun <P: HttpParam, reified T : Any> delete(
        url: String, params: P,
        headers: Map<String, Any>? = null,
        callback: HttpCallback<P, T>? = null
    ) : HttpCancellable {
        return client.sendHttpRequest(wrapUrl(url), HttpMethod.DELETE, T::class.java, params, headers, callback)
    }
}