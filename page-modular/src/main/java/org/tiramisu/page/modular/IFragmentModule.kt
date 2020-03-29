package org.tiramisu.page.modular

import android.view.View

/**
 *
 * @author felixxfwang
 * @date   2019-12-12
 */
interface IFragmentModule : IPageModule {

    /**
     * 对应fragment的onCreateView
     */
    fun onCreateView(view: View?) {}

    /**
     * 对应的onPageViewRefresh
     */
    fun onViewRefresh() {}

    /**
     * 可见性变化
     */
    fun onHiddenChanged(hidden: Boolean) {}

    /**
     * 对应fragment的onDestroyView
     */
    fun onPageDestroyView() {}
}
