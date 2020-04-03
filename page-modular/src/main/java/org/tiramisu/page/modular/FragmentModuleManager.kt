package org.tiramisu.page.modular

import android.view.View

open class FragmentModuleManager : AbstractModuleManager(), IFragmentModule {

    protected val modules by lazy { ArrayList<IFragmentModule>() }

    override fun addModule(module: IPageModule) {
        super.addModule(module)
        if (module is IFragmentModule) {
            modules.add(module)
        }
    }

    override fun clear() {
        modules.clear()
    }

    override fun onFragmentCreated() {
        modules.forEach { it.onFragmentCreated() }
    }

    override fun onViewCreated(view: View) {
        modules.forEach { it.onViewCreated(view) }
    }

    override fun onViewRefresh() {
        modules.forEach { it.onViewRefresh() }
    }

    override fun onFragmentShow() {
        modules.forEach { it.onFragmentShow() }
    }

    override fun onFragmentHide() {
        modules.forEach { it.onFragmentHide() }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        modules.forEach { it.onHiddenChanged(hidden) }
    }

    override fun onViewDestroyed() {
        modules.forEach { it.onViewDestroyed() }
    }

    override fun onFragmentDestroyed() {
        modules.forEach { it.onFragmentDestroyed() }
    }
}