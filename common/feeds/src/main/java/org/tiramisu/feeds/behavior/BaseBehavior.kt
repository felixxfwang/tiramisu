package org.tiramisu.feeds.behavior

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.tiramisu.feeds.data.IChannelModel
import org.tiramisu.feeds.holder.BaseFeedViewHolder
import org.tiramisu.feeds.holder.BaseViewHolder

abstract class BaseBehavior<T> : IListLifecycleBehavior {

    protected fun <R : View> findView(id: Int, viewHolder: RecyclerView.ViewHolder?): R? {
        return if (viewHolder is BaseFeedViewHolder<*>) {
            viewHolder.findView(id)
        } else {
            viewHolder?.itemView?.findViewById(id) as? R
        }
    }

    protected fun findData(viewHolder: RecyclerView.ViewHolder?): T? {
        if (viewHolder is BaseViewHolder<*>) {
            val data = viewHolder.data
            return data as? T
        }
        return null
    }

    final override fun onViewClick(view: View, viewHolder: RecyclerView.ViewHolder) {
        dispatch(viewHolder) { onViewClick(view, it, viewHolder, view.context) }
    }

    final override fun onViewLongClick(view: View, viewHolder: RecyclerView.ViewHolder) {
        dispatch(viewHolder) { onViewLongClick(view, it, viewHolder, view.context) }
    }

    final override fun onItemViewClick(view: View, viewHolder: RecyclerView.ViewHolder) {
        dispatch(viewHolder) { onItemViewClick(view, it, viewHolder, view.context) }
    }

    final override fun onItemViewLongClick(view: View, viewHolder: RecyclerView.ViewHolder) {
        dispatch(viewHolder) { onItemViewLongClick(view, it, viewHolder, view.context) }
    }

    override fun onListShow(list: RecyclerView?, channel: String?) {
    }

    override fun onBottomIdleInScreen(
        viewHolder: RecyclerView.ViewHolder?,
        channel: String?,
        top: Int,
        bottom: Int,
        containerTop: Int,
        containerBottom: Int
    ) {
    }

    override fun onListHide(list: RecyclerView?, channel: String?) {
    }

    override fun onListScrollStateIdle(list: RecyclerView?, channel: String?) {
    }

    override fun onAttachedToWindow(viewHolder: RecyclerView.ViewHolder?) {
    }

    override fun onDetachedFromWindow(viewHolder: RecyclerView.ViewHolder?) {
    }

    override fun onListScrolled(list: RecyclerView?, channel: String?, dx: Int, dy: Int) {
    }

    override fun onListRefresh(list: RecyclerView?, channel: String?) {
    }

    override fun onFullIdleInScreen(
        viewHolder: RecyclerView.ViewHolder?,
        channel: String?,
        top: Int,
        bottom: Int,
        containerTop: Int,
        containerBottom: Int
    ) {
    }

    override fun onDismiss(viewHolder: RecyclerView.ViewHolder?) {
    }

    override fun onExposure(list: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
    }

    override fun bindChannelModel(channelModel: IChannelModel?) {
    }

    override fun onScrollDetachedFromWindow(viewHolder: RecyclerView.ViewHolder?) {
    }

    override fun onListScrollStateChanged(list: RecyclerView?, channel: String?, newState: Int) {
    }

    override fun onListDestroy(list: RecyclerView?, channel: String?) {
    }

    private fun dispatch(
        viewHolder: RecyclerView.ViewHolder,
        function: (T) -> Unit
    ) {
        val data = findData(viewHolder) ?: return
        function.invoke(data)
    }

    /**
     * 普通View点击事件处理
     */
    protected open fun onViewClick(view: View, data: T, holder: RecyclerView.ViewHolder, activity: Context) {}

    /**
     * ItemView点击事件处理
     */
    protected open fun onItemViewClick(view: View, data: T, holder: RecyclerView.ViewHolder, activity: Context) {}

    /**
     * itemView长按
     */
    protected open fun onItemViewLongClick(view: View, data: T, holder: RecyclerView.ViewHolder, activity: Context) {}

    /**
     * 某一个子View长按
     */
    protected open fun onViewLongClick(view: View, data: T, holder: RecyclerView.ViewHolder, activity: Context) {}

}
