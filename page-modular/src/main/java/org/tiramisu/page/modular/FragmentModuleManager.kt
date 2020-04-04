package org.tiramisu.page.modular

import android.view.View
import org.tiramisu.page.modular.visibility.FragmentVisibilityModule
import org.tiramisu.page.modular.visibility.VisibilityChangedListener

open class FragmentModuleManager : AbstractModuleManager(), IFragmentModule, VisibilityChangedListener {

    protected val modules by lazy {
        ArrayList<IFragmentModule>().also { addModule(visibilityModule) }
    }

    private var visibilityChangedListener: VisibilityChangedListener? = null

    private val visibilityModule by lazy {
        FragmentVisibilityModule().also { it.setVisibilityChangedListener(this) }
    }

    fun setVisibilityChangedListener(listener: VisibilityChangedListener) {
        this.visibilityChangedListener = listener
    }

    fun isFragmentVisible(): Boolean = visibilityModule.isFragmentVisible()

    override fun onVisibilityChanged(isVisible: Boolean) {
        visibilityChangedListener?.onVisibilityChanged(isVisible)
        if (isVisible) {
            modules.forEach { it.onFragmentShow() }
        } else {
            modules.forEach { it.onFragmentHide() }
        }
    }

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

    override fun onFragmentResume() {
        modules.forEach { it.onFragmentResume() }
    }

    override fun onFragmentPause() {
        modules.forEach { it.onFragmentPause() }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        modules.forEach { it.onHiddenChanged(hidden) }
    }

    override fun onSetUserVisibleHint(visible: Boolean) {
        modules.forEach { it.onSetUserVisibleHint(visible) }
    }

    override fun onViewDestroyed() {
        modules.forEach { it.onViewDestroyed() }
    }

    override fun onFragmentDestroyed() {
        modules.forEach { it.onFragmentDestroyed() }
    }
}