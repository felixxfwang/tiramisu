package org.tiramisu.repository

abstract class BaseDataRepository<P, D>(
    protected val client: DataClient
) : DataRepository<P, D> {

    protected var request: Disposable? = null

    override fun cancel() {
        if (request?.isDisposed() == false) {
            request?.dispose()
        }
    }
}