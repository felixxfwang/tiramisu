package org.tiramisu.repository

import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseCoroutineSource<P, D, REQ, RSP: Any> : BaseDataSource<P, D, REQ, RSP>(), CoroutineDataSource<P, D> {

    final override fun loadData(param: P, callback: LoadDataCallback<P, D>?) {}

    private val isLoading = AtomicBoolean(false)

    override fun isLoading(): Boolean = isLoading.get()

    override suspend fun loadData(param: P): DataResult<D> {
        isLoading.set(true)
        val result = when (val response = client.sendCoroutineRequest(getRequest(param))) {
            is Result.Success -> DataResult.success(getResponse(response.get()))
            is Result.Failure -> DataResult.error(response.getException())
        }
        isLoading.set(false)
        return result
    }

    protected abstract fun getRequest(param: P): REQ

    protected abstract fun getResponse(response: RSP): D

}