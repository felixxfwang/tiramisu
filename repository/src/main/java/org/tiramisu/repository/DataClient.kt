package org.tiramisu.repository

interface DataClient {

    fun <PARAM, RESULT> sendDataRequest(param: PARAM, callback: DataCallback<PARAM, RESULT>): Disposable
}