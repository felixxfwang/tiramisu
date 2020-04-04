package org.tiramisu.feeds.adapter

import androidx.recyclerview.widget.RecyclerView
import org.tiramisu.feeds.adapter.plugin.BaseAdapterDecorator
import org.tiramisu.feeds.adapter.plugin.CompositeAdapterDecorator
import org.tiramisu.feeds.data.BaseAdapterData

/**
 *
 * @author felixxfwang
 * @date   2019-09-12
 */
abstract class BaseAdapter<T : BaseAdapterData, VH : RecyclerView.ViewHolder> : AbsAdapter<T, VH>() {

    companion object {
        private const val TAG = "AbstractAdapter"
    }

    private val mData = ArrayList<T>()

    protected val plugins = CompositeAdapterDecorator<T>()

    override fun getItem(position: Int): T = mData[position]

    override fun getItemViewType(position: Int): Int = getItem(position).showType()

    override fun getItemCount(): Int = mData.size

    /**
     * 添加插件
     */
    final override fun addDecorator(decorator: BaseAdapterDecorator<T>) {
        plugins.addPlugin(decorator)
    }

    final override fun removeDecorator(decorator: BaseAdapterDecorator<T>) {
        plugins.removePlugin(decorator)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        plugins.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        plugins.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        mData.getOrNull(holder.adapterPosition)?.let { data ->
            plugins.onDataScrolledIn(data)
        }
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        mData.getOrNull(holder.adapterPosition)?.let { data ->
            plugins.onDataScrolledOut(data)
        }
    }

    /**
     * 重置adapter数据
     */
    override fun setAdapterData(data: List<T>) {
        val mutableList = data.toMutableList()
        if (mData.isNotEmpty()) {
            mData.clear()
        }
        plugins.onPreSetAdapterData(mutableList)
        mData.addAll(mutableList)
        plugins.onSetAdapterData(mData)

        notifyDataSetChanged()
    }

    /**
     * 新增Adapter数据。
     */
    override fun addAdapterData(data: List<T>, notifyUI: Boolean) {
        val mutableList = data.toMutableList()
        plugins.onAddAdapterData(mData, mutableList)
        val start = mData.size
        mData.addAll(mutableList)

        if (notifyUI) {
            notifyItemRangeInserted(start, mutableList.size)
        }
    }

    /**
     * 将数据添加到列表末尾
     */
    override fun addAdapterData(data: T, notifyUI: Boolean) {
        val position = mData.size
        val addedDataList = listOf(data)
        plugins.onPreInsertAdapterData(mData, addedDataList, position)
        mData.add(data)
        plugins.onPostInsertAdapterData(mData, addedDataList, position)

        if (notifyUI) {
            notifyItemInserted(position)
        }
    }

    /**
     * 更新Adapter结果。
     */
    override fun replaceAdapterData(position: Int, data: T, notifyUI: Boolean) {
        if (mData.isNotEmpty() && mData.size > position) {
            val oldData = mData[position]
            mData[position] = data
            plugins.onReplaceAdapterData(mData, oldData, data, position)

            if (notifyUI) {
                notifyItemChanged(position)
            }
        }
    }

    /**
     * 插入某个Item数据。
     */
    override fun insertAdapterData(position: Int, data: T, notifyUI: Boolean) {
        if (mData.isEmpty()) {
            addAdapterData(0, data, notifyUI)
        } else {
            addAdapterData(position, data, notifyUI)
        }
    }

    private fun addAdapterData(position: Int, data: T, notifyUI: Boolean) {
        if (position >= 0 && position <= mData.size) {
            val addedDataList = listOf(data)
            plugins.onPreInsertAdapterData(mData, addedDataList, position)
            mData.add(position, data)
            plugins.onPostInsertAdapterData(mData, addedDataList, position)

            if (notifyUI) {
                if (position == 0) {
                    notifyDataSetChanged()
                } else {
                    notifyItemInserted(position)
                }
            }
        }
    }

    /**
     * 在某个位置插入多个数据
     */
    override fun insertAdapterData(position: Int, dataList: List<T>) {
        if (mData.isEmpty()) {
            addAdapterData(0, dataList)
        } else {
            addAdapterData(position, dataList)
        }
    }

    private fun addAdapterData(position: Int, dataList: List<T>) {
        if (position >= 0 && position <= mData.size && dataList.isNotEmpty()) {
            plugins.onPreInsertAdapterData(mData, dataList, position)
            mData.addAll(position, dataList)
            plugins.onPostInsertAdapterData(mData, dataList, position)

            notifyItemRangeInserted(position, dataList.size)
        }
    }

    /**
     * 删除某个item数据
     */
    override fun removeAdapterData(position: Int, notifyUI: Boolean) {
        if (mData.isNotEmpty() && position in 0 until mData.size) {
            val removedData = mData.removeAt(position)
            plugins.onRemoveAdapterData(mData, removedData, position)

            if (notifyUI) {
                notifyItemRemoved(position)
            }
        }
    }

    /**
     * 删除某个item数据
     */
    override fun removeAdapterData(data: T, notifyUI: Boolean): Boolean {
        val position = mData.indexOf(data)
        removeAdapterData(position, notifyUI)
        return position > -1
    }

    /**
     * 将某个位置的数据移动到另一个位置
     */
    override fun moveAdapterData(fromIndex: Int, toIndex: Int) {
        if (fromIndex in 0 until mData.size && toIndex in 0 until mData.size) {
            val item = mData.removeAt(fromIndex)
            mData.add(toIndex, item)
            plugins.onMoveAdapterData(mData, item, fromIndex, toIndex)

            notifyItemMoved(fromIndex, toIndex)
        }
    }

    /**
     * 获取整个数据列表，这里返回值一定是List<T>类型，不允许外部对mData内容进行修改
     */
    override fun getDataList(): List<T> = mData

    /**
     * 删除整个数据列表
     */
    override fun removeAllAdapterData() {
        mData.clear()
        notifyDataSetChanged()
    }
}
