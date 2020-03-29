package org.tiramisu.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseCoroutineRepository<P, D, REQ, RSP: Any> : BaseDataRepository<P, D, REQ, RSP>() {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun cancel() {
        scope.cancel()
    }

}