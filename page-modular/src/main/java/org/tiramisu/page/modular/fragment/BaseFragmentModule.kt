package org.tiramisu.page.modular.fragment

import org.tiramisu.page.modular.IPageModuleManager

abstract class BaseFragmentModule : IFragmentModule {
    private var eventBus: IPageModuleManager? = null

    override fun setModuleManager(manager: IPageModuleManager) {
        this.eventBus = manager
    }

    protected fun <T> getBus() = eventBus as T
}