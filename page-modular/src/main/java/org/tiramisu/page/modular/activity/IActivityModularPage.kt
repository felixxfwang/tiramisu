package org.tiramisu.page.modular.activity

import java.util.*

interface IActivityModularPage {
    companion object {
        internal val modulesMap = IdentityHashMap<IActivityModularPage, ActivityModuleManager>()
    }

    val modular: ActivityModuleManager
        get() = modulesMap[this] ?: run {
            (onCreateModuleManager() ?: ActivityModuleManager()).also { modulesMap[this] = it }
        }

    fun onCreateModuleManager(): ActivityModuleManager? = null
}