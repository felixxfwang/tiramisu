package org.tiramisu.feeds.adapter.wrapper

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import org.tiramisu.feeds.adapter.BaseAdapter
import org.tiramisu.feeds.adapter.IAdapter
import org.tiramisu.feeds.adapter.AbsAdapter
import org.tiramisu.feeds.data.BaseAdapterData

/**
 *
 * @author felixxfwang
 * @date   2019-10-11
 */
open class BaseWrapperAdapter<T: BaseAdapterData, VH: RecyclerView.ViewHolder>(
        val innerAdapter: BaseAdapter<T, VH>
) : AbsAdapter<T, VH>(), IAdapter<T> by innerAdapter {

    init {
        innerAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                this@BaseWrapperAdapter.onChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                this@BaseWrapperAdapter.onItemRangeChanged(positionStart, itemCount, payload)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                this@BaseWrapperAdapter.onItemRangeChanged(positionStart, itemCount)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                this@BaseWrapperAdapter.onItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                this@BaseWrapperAdapter.onItemRangeRemoved(positionStart, itemCount)
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                this@BaseWrapperAdapter.onItemRangeMoved(fromPosition, toPosition, itemCount)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return innerAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        innerAdapter.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {
        return innerAdapter.itemCount
    }

    override fun getItemViewType(position: Int): Int {
        return innerAdapter.getItemViewType(position)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        innerAdapter.setHasStableIds(hasStableIds)
    }

    override fun getItemId(position: Int): Long {
        return innerAdapter.getItemId(position)
    }

    override fun onViewRecycled(holder: VH) {
        innerAdapter.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: VH): Boolean {
        return innerAdapter.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: VH) {
        innerAdapter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        innerAdapter.onViewDetachedFromWindow(holder)
    }

    // 这两个方法不能被代理哦，否则WrapperAdapter的notify方法会失效
//    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
//        innerAdapter.registerAdapterDataObserver(observer)
//    }
//
//    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
//        innerAdapter.unregisterAdapterDataObserver(observer)
//    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        innerAdapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        innerAdapter.onDetachedFromRecyclerView(recyclerView)
    }

    protected open fun onChanged() {
        notifyDataSetChanged()
    }

    protected open fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
        notifyItemRangeChanged(positionStart, itemCount, payload)
    }

    protected open fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        notifyItemRangeChanged(positionStart, itemCount)
    }

    protected open fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        notifyItemRangeInserted(positionStart, itemCount)
    }

    protected open fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        notifyItemRangeRemoved(positionStart, itemCount)
    }

    protected open fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        notifyItemMoved(fromPosition, toPosition)
    }
}
