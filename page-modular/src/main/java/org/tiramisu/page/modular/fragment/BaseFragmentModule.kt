package org.tiramisu.page.modular.fragment

import org.tiramisu.page.modular.IPageModuleManager

abstract class BaseFragmentModule<T: IPageModuleManager> : IFragmentModule {
    private var eventBus: T? = null

    fun setModuleManager(manager: T) {
        this.eventBus = manager
    }

    protected fun bus() = eventBus
}