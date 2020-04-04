package org.tiramisu.page.modular.activity

import org.tiramisu.page.modular.IPageModuleManager

abstract class BaseActivityModule : IActivityModule {

    private var eventBus: IPageModuleManager? = null

    override fun setModuleManager(manager: IPageModuleManager) {
        this.eventBus = manager
    }

    protected fun <T> getBus() = eventBus as T
}