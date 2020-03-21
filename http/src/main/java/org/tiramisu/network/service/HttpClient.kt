package org.tiramisu.network.service

interface HttpClient {

    fun <T: Any> sendHttpRequest(
        url: String,
        method: HttpMethod,
        clazz: Class<T>,
        params: Map<String, String>,
        headers: Map<String, String>?,
        callback: HttpCallback<T>?
    )
}