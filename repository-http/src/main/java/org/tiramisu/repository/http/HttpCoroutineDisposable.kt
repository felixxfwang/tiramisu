package org.tiramisu.repository.http

import kotlinx.coroutines.Job
import org.tiramisu.repository.Disposable

class HttpCoroutineDisposable(private val job: Job) : Disposable {
    override fun isDisposed(): Boolean {
        return job.isCancelled || job.isCompleted
    }

    override fun dispose() {
        job.cancel()
    }

}