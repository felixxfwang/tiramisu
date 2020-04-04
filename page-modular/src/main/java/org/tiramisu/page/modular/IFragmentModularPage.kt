package org.tiramisu.page.modular

import java.util.*

interface IFragmentModularPage {
    companion object {
        internal val modulesMap = IdentityHashMap<IFragmentModularPage, FragmentModuleManager>()
    }

    val modular: FragmentModuleManager
        get() = modulesMap[this] ?: run {
            (getModuleManager() ?: FragmentModuleManager()).also { modulesMap[this] = it }
        }

    fun getModuleManager(): FragmentModuleManager? = null
}