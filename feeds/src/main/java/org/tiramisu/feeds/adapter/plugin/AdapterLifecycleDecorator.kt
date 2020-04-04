package org.tiramisu.feeds.adapter.plugin

import androidx.recyclerview.widget.RecyclerView
import org.tiramisu.feeds.data.BaseAdapterData

/**
 *
 * @author felixxfwang
 * @date   2019-10-12
 */
interface AdapterLifecycleDecorator<T : BaseAdapterData> : BaseAdapterDecorator<T> {

    /**
     * 数据项划出屏幕
     */
    fun onDataScrolledOut(data: T) {}

    /**
     * 数据项划进屏幕
     */
    fun onDataScrolledIn(data: T) {}


    /**
     * 列表滑动状态改变
     */
    fun onAdapterScrollStateChanged(recyclerView: RecyclerView, newState: Int) {}

    /**
     * 列表产生滑动
     */
    fun onAdapterScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {}

    /**
     * 列表滑动到顶部
     */
    fun onAdapterScrolledToTop(recyclerView: RecyclerView) {}

    /**
     * 列表滑动到底部
     */
    fun onAdapterScrolledToBottom(recyclerView: RecyclerView) {}


    /**
     * 列表页面可见
     */
    fun onAdapterPageShow() {}

    /**
     * 列表页面隐藏
     */
    fun onAdapterPageHide() {}


    /**
     * Adapter附着到RecyclerView上
     */
    fun onAttachedToRecyclerView(recyclerView: RecyclerView) {}

    /**
     * Adapter从RecyclerView上分离
     */
    fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {}
}
