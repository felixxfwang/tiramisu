package org.tiramisu.repository.coroutine

import org.tiramisu.repository.DataResult
import org.tiramisu.repository.Result

abstract class BaseCoroutineDataSource<P, D, REQ, RSP> : CoroutineDataSource<P, D> {

    private val client: CoroutineDataClient<REQ, RSP> by lazy { getDataClient() }

    override suspend fun loadData(param: P): DataResult<D> {
        return when (val response = client.sendRequest(getRequest(param))) {
            is Result.Success -> Result.success(
                getResponse(response.get())
            )
            is Result.Failure -> Result.error(
                response.getException()
            )
        }
    }

    protected abstract fun getDataClient(): CoroutineDataClient<REQ, RSP>

    protected abstract fun getRequest(param: P): REQ

    protected abstract fun getResponse(response: RSP): D

}