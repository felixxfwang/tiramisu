package org.tiramisu.repository.http

import org.tiramisu.http.HttpCancellable
import org.tiramisu.repository.Disposable

class HttpDisposable(private val request: HttpCancellable) : Disposable {

    override fun isDisposed(): Boolean = false

    override fun dispose() {
        request.cancel()
    }
}