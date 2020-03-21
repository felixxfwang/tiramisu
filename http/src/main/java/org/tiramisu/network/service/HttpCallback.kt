package org.tiramisu.network.service

interface HttpCallback<T> {

    fun onSuccess(data: T)

    fun onError(errorCode: Int, errorMessage: String?)
}