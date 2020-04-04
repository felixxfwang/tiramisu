package org.tiramisu.page.modular

import java.util.*

interface IActivityModularPage {
    companion object {
        internal val modulesMap = IdentityHashMap<IActivityModularPage, ActivityModuleManager>()
    }

    val modular: ActivityModuleManager
        get() = modulesMap[this] ?: run {
            (getModularManager() ?: ActivityModuleManager()).also { modulesMap[this] = it }
        }

    fun getModularManager(): ActivityModuleManager? = null
}