package org.tiramisu.page.modular

import java.util.*

interface FragmentModularPage<M: FragmentModuleManager> {
    companion object {
        internal val modulesMap = IdentityHashMap<FragmentModularPage<*>, IPageModuleManager>()
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