package org.tiramisu.network.service

interface HttpCallback<P: HttpParam, T> {

    fun onSuccess(param: P, data: T)

    fun onError(param: P, errorCode: Int, errorMessage: String?)
}