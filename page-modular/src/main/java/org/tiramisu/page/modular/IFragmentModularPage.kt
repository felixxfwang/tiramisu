package org.tiramisu.page.modular

import org.tiramisu.page.modular.visibility.VisibilityChangedListener
import java.util.*

interface IFragmentModularPage : VisibilityChangedListener {
    companion object {
        internal val modulesMap = IdentityHashMap<IFragmentModularPage, FragmentModuleManager>()
    }

    val modular: FragmentModuleManager
        get() = modulesMap[this] ?: run {
            (onCreateModuleManager() ?: FragmentModuleManager()).also {
                modulesMap[this] = it
                it.setVisibilityChangedListener(this)
            }
        }

    fun onCreateModuleManager(): FragmentModuleManager? = null

    fun isFragmentVisible(): Boolean = modular.isFragmentVisible()
}