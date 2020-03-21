package org.tiramisu.network.service

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

    inline fun <reified T : Any> get(
        url: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String>? = null,
        callback: HttpCallback<T>? = null
    ) {
        client.sendHttpRequest(wrapUrl(url), HttpMethod.GET, T::class.java, params, headers, callback)
    }

    inline fun <reified T : Any> post(
        url: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String>? = null,
        callback: HttpCallback<T>? = null
    ) {
        client.sendHttpRequest(wrapUrl(url), HttpMethod.POST, T::class.java, params, headers, callback)
    }

    inline fun <reified T : Any> put(
        url: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String>? = null,
        callback: HttpCallback<T>? = null
    ) {
        client.sendHttpRequest(wrapUrl(url), HttpMethod.PUT, T::class.java, params, headers, callback)
    }

    inline fun <reified T : Any> delete(
        url: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String>? = null,
        callback: HttpCallback<T>? = null
    ) {
        client.sendHttpRequest(wrapUrl(url), HttpMethod.DELETE, T::class.java, params, headers, callback)
    }
}