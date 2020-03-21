package org.tiramisu.feeds.adapter.wrapper

import android.util.SparseArray
import android.view.View
import androidx.core.util.set
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.tiramisu.feeds.adapter.BaseAdapter
import org.tiramisu.feeds.data.BaseAdapterData
import org.tiramisu.feeds.holder.AbstractViewHolder
import org.tiramisu.feeds.holder.BaseViewHolder

/**
 *
 * @author felixxfwang
 * @date   2019-10-11
 */
class HeaderFooterWrapperAdapter<T : BaseAdapterData>(innerAdapter: BaseAdapter<T, AbstractViewHolder>) :
        BaseWrapperAdapter<T, AbstractViewHolder>(innerAdapter) {

    private val mHeaderDataList = ArrayList<T>()
    private val mFooterDataList = ArrayList<T>()
    private val mViewMap = SparseArray<View>()

    private val dataCount get() = innerAdapter.itemCount
    private val headerCount get() = mHeaderDataList.size
    private val footerCount get() = mFooterDataList.size

    private fun isHeader(position: Int) = position in 0 until headerCount
    private fun isFooter(position: Int) = position in itemCount - footerCount until itemCount
    private fun isNormal(position: Int) = position in headerCount until headerCount + dataCount

    private fun getDataIndex(position: Int) = position - headerCount

    /**
     * 根据data添加header
     */
    fun addHeader(data: T) {
        if (!mHeaderDataList.contains(data)) {
            mHeaderDataList.add(data)
            notifyItemInserted(headerCount - 1)
        }
    }

    /**
     * 根据data添加footer
     */
    fun addFooter(data: T) {
        if (!mFooterDataList.contains(data)) {
            mFooterDataList.add(data)
            notifyItemInserted(itemCount - 1)
        }
    }

    /**
     * 根据data移除header
     */
    fun removeHeader(data: T) {
        val index = mHeaderDataList.indexOf(data)
        if (index > -1) {
            mHeaderDataList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    /**
     * 根据data移除footer
     */
    fun removeFooter(data: T) {
        val index = mFooterDataList.indexOf(data)
        if (index > -1) {
            mFooterDataList.removeAt(index)
            notifyItemRemoved(headerCount + dataCount + index)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            isHeader(position) -> mHeaderDataList.getOrNull(position)?.showType() ?: 0
            isFooter(position) -> mFooterDataList.getOrNull(position - headerCount - dataCount)?.showType() ?: 0
            else -> super.getItemViewType(getDataIndex(position))
        }
    }

    override fun getItemCount(): Int = headerCount + dataCount + footerCount

    override fun onBindViewHolder(holder: AbstractViewHolder, position: Int, payloads: MutableList<Any>) {
        when {
            isHeader(position) -> {
                val data = mHeaderDataList.getOrNull(position) ?: return
                mViewMap[data.showType()] = holder.itemView
                bindHeaderOrFooterViewHolder(holder, position, payloads, data)
            }
            isFooter(position) -> {
                val data = mFooterDataList.getOrNull(position - headerCount - dataCount) ?: return
                mViewMap[data.showType()] = holder.itemView
                bindHeaderOrFooterViewHolder(holder, position, payloads,data)
            }
            else -> super.onBindViewHolder(holder, getDataIndex(position), payloads)
        }
    }

    private fun bindHeaderOrFooterViewHolder(holder: AbstractViewHolder, position: Int, payloads: MutableList<Any>, data: T) {
        val vh = holder as? BaseViewHolder<T>
        vh?.setChannelData(innerAdapter.getChannelData())
        vh?.checkAndBindData(data, position, payloads) // 这个方法里面有try catch 所以可以传null
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        // Grid布局下，Header和Footer设置为占满一行
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val oldLookup = layoutManager.spanSizeLookup
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (isHeader(position) || isFooter(position)) {
                        return layoutManager.spanCount
                    }
                    return oldLookup?.getSpanSize(position) ?: 1
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: AbstractViewHolder) {
        super.onViewAttachedToWindow(holder)

        // 瀑布流布局下，Header和Footer设置为占满一行
        val position = holder.layoutPosition
        if (isHeader(position) || isFooter(position)) {
            val lp = holder.itemView.layoutParams
            if (lp is StaggeredGridLayoutManager.LayoutParams) {
                lp.isFullSpan = true
                holder.itemView.layoutParams = lp
            }
        }
    }

    override fun replaceAdapterData(position: Int, data: T, notifyUI: Boolean) {
        if (isNormal(position)) {
            innerAdapter.replaceAdapterData(getDataIndex(position), data, notifyUI)
        }
    }

    override fun insertAdapterData(position: Int, data: T, notifyUI: Boolean) {
        if (isNormal(position)) {
            innerAdapter.insertAdapterData(getDataIndex(position), data, notifyUI)
        }
    }

    override fun removeAdapterData(position: Int, notifyUI: Boolean) {
        if (isNormal(position)) {
            innerAdapter.removeAdapterData(getDataIndex(position), notifyUI)
        }
    }

    override fun moveAdapterData(fromIndex: Int, toIndex: Int) {
        if (isNormal(fromIndex) && isNormal(toIndex)) {
            innerAdapter.moveAdapterData(getDataIndex(fromIndex), getDataIndex(toIndex))
        }
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
        super.onItemRangeChanged(positionStart + headerCount, itemCount, payload)
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        super.onItemRangeChanged(positionStart + headerCount, itemCount)
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        super.onItemRangeInserted(positionStart + headerCount, itemCount)
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        super.onItemRangeRemoved(positionStart + headerCount, itemCount)
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        super.onItemRangeMoved(fromPosition + headerCount, toPosition + headerCount, itemCount)
    }
}
