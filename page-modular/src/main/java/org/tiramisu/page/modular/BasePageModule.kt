package org.tiramisu.page.modular

import kotlin.properties.Delegates

/**
 *
 * @author felixxfwang
 * @date   2019-12-11
 */
abstract class BasePageModule : IPageModule {

    protected var eventBus: Any by Delegates.notNull()

    /**
     * 设置当前module所属的manager
     */
    internal fun setModuleManager(manager: IPageModuleManager) {
        this.eventBus = manager
    }

    protected inline fun <reified T> getBus(): T = eventBus as T
}
