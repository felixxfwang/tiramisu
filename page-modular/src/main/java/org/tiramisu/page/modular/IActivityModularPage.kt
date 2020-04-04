package org.tiramisu.page.modular

import java.util.*

typealias ActivityModularPage = IActivityModularPage<ActivityModuleManager>

interface IActivityModularPage<M: ActivityModuleManager> {
    companion object {
        internal val modulesMap = IdentityHashMap<IActivityModularPage<*>, ActivityModuleManager>()
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