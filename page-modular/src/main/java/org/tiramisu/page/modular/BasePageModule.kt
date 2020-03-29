package org.tiramisu.page.modular

/**
 *
 * @author felixxfwang
 * @date   2019-12-11
 */
abstract class BasePageModule : IPageModule {
    protected var manager: IPageModuleManager? = null

    /**
     * 设置当前module所属的manager
     */
    fun setModuleManager(manager: IPageModuleManager) {
        this.manager = manager
    }

    protected inline fun <reified T> manager(): T? = manager as? T

    protected fun getPageId() = manager<IPageInfo>()?.getPageId() ?: ""
}
