package org.tiramisu.repository.http

import org.tiramisu.http.HttpParam
import org.tiramisu.repository.coroutine.BaseCoroutineDataSource
import org.tiramisu.repository.coroutine.CoroutineDataClient

abstract class BaseHttpCoroutineDataSource <P, D, REQ: HttpParam, RSP>(
    private val baseUrl: String,
    private val path: String,
    private val rspClass: Class<RSP>
) : BaseCoroutineDataSource<P, D, REQ, RSP>() {

    override fun getDataClient(): CoroutineDataClient<REQ, RSP> {
        return HttpCoroutineDataClient(baseUrl, path, rspClass)
    }
}