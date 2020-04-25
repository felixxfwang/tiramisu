package org.tiramisu.repository.http

import org.tiramisu.http.HttpParam
import org.tiramisu.repository.BaseDataSource
import org.tiramisu.repository.DataClient

abstract class BaseHttpDataSource<P, D, REQ: HttpParam, RSP: Any>(
    private val baseUrl: String,
    private val path: String,
    private val rspClass: Class<RSP>
) : BaseDataSource<P, D, REQ, RSP>() {

    override fun getDataClient(): DataClient<REQ, RSP> {
        return HttpDataClient(baseUrl, path, rspClass)
    }
}