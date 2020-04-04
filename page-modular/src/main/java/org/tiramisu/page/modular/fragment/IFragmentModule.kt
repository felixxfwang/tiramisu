package org.tiramisu.page.modular.fragment

import android.view.View
import org.tiramisu.page.modular.IPageModule

/**
 *
 * @author felixxfwang
 * @date   2019-12-12
 */
interface IFragmentModule : IPageModule {

    fun onFragmentCreated() {}

    fun onViewCreated(view: View) {}

    fun onViewRefresh() {}

    fun onFragmentShow() {}

    fun onFragmentHide() {}

    fun onFragmentResume() {}

    fun onFragmentPause() {}

    fun onHiddenChanged(hidden: Boolean) {}

    fun onSetUserVisibleHint(visible: Boolean) {}

    fun onViewDestroyed() {}

    fun onFragmentDestroyed() {}
}
