package org.tiramisu.modular

import android.app.Application
import androidx.annotation.CallSuper

abstract class BaseModule: IModule {

    private var initialized = false

    override fun isInitialized(): Boolean = initialized

    @CallSuper
    override fun initialize(application: Application) {
        this.initialized = true
    }
}