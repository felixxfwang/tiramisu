package org.tiramisu.page.modular

import java.util.*

typealias FragmentModularPage = IFragmentModularPage<FragmentModuleManager>

interface IFragmentModularPage<M: FragmentModuleManager> {
    companion object {
        internal val modulesMap = IdentityHashMap<IFragmentModularPage<*>, IPageModuleManager>()
    }

    @Suppress("UNCHECKED_CAST")
    val modular: M
        get() {
            if (modulesMap[this] == null) {
                modulesMap[this] = buildModuleManager()
            }
            return modulesMap as M
        }

    fun buildModuleManager(): M
}