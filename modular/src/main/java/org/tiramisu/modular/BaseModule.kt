package org.tiramisu.modular

abstract class BaseModule: IModule {

    private var initialized = false

    override fun isInitialized(): Boolean = initialized

    protected fun setInitialized() {
        this.initialized = true
    }
}