package org.tiramisu.repository

interface DataClient<PARAM, RESULT> {

    fun sendDataRequest(param: PARAM, callback: DataCallback<PARAM, RESULT>): Disposable
}