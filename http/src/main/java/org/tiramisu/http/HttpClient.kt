package org.tiramisu.http

interface HttpClient {

    fun <P: HttpParam, T: Any> sendHttpRequest(
        url: String,
        method: HttpMethod,
        clazz: Class<T>,
        params: P,
        headers: Map<String, Any>?,
        callback: HttpCallback<P, T>?
    )
}