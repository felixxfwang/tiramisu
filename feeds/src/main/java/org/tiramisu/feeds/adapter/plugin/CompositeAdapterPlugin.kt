package org.tiramisu.feeds.adapter.plugin

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.tiramisu.feeds.adapter.BaseAdapter
import org.tiramisu.feeds.data.BaseAdapterData
import org.tiramisu.feeds.data.WriteBackData

/**
 * 聚合的AdapterPlugin
 *
 * @author felixxfwang
 * @date   2019-09-25
 */
class CompositeAdapterPlugin<T : BaseAdapterData> : AdapterPlugin<T> {

    private val dataPlugins = ArrayList<AdapterDataPlugin<T>>()
    private val lifecyclePlugins = ArrayList<AdapterLifecyclePlugin<T>>()
    private val writeBackPlugins = ArrayList<AdapterWriteBackPlugin<T>>()
    private val viewHolderPlugins = ArrayList<AdapterViewHolderPlugin<T>>()

    /**
     * 添加插件
     */
    fun addPlugin(plugin: BaseAdapterPlugin<T>) {
        if (plugin is AdapterDataPlugin && canAddPlugin(dataPlugins, plugin)) {
            dataPlugins.add(plugin)
        }
        if (plugin is AdapterLifecyclePlugin && canAddPlugin(lifecyclePlugins, plugin)) {
            lifecyclePlugins.add(plugin)
        }
        if (plugin is AdapterWriteBackPlugin && canAddPlugin(writeBackPlugins, plugin)) {
            writeBackPlugins.add(plugin)
        }
        if (plugin is AdapterViewHolderPlugin && canAddPlugin(viewHolderPlugins, plugin)) {
            viewHolderPlugins.add(plugin)
        }
    }

    /**
     * 删除插件
     */
    fun removePlugin(plugin: BaseAdapterPlugin<T>) {
        if (plugin is AdapterDataPlugin) {
            dataPlugins.remove(plugin)
        }
        if (plugin is AdapterLifecyclePlugin) {
            lifecyclePlugins.remove(plugin)
        }
        if (plugin is AdapterWriteBackPlugin) {
            writeBackPlugins.remove(plugin)
        }
        if (plugin is AdapterViewHolderPlugin) {
            viewHolderPlugins.remove(plugin)
        }
    }

    private fun canAddPlugin(plugins: List<BaseAdapterPlugin<T>>, plugin: BaseAdapterPlugin<T>): Boolean {
        // 同一类型的插件只允许添加一个
        return !plugins.contains(plugin) && plugins.none { it.javaClass == plugin.javaClass }
    }

    /**
     * 清除所有插件
     */
    fun clear() {
        dataPlugins.clear()
        lifecyclePlugins.clear()
        writeBackPlugins.clear()
        viewHolderPlugins.clear()
    }

    override fun onPreSetAdapterData(addedDataList: MutableList<T>) {
        dataPlugins.forEach { it.onPreSetAdapterData(addedDataList) }
    }

    override fun onSetAdapterData(dataList: List<T>) {
        dataPlugins.forEach { it.onSetAdapterData(dataList) }
    }

    override fun onAddAdapterData(dataList: List<T>, addedDataList: MutableList<T>) {
        dataPlugins.forEach { it.onAddAdapterData(dataList, addedDataList) }
    }

    override fun onPreInsertAdapterData(dataList: List<T>, addedDataList: List<T>, position: Int) {
        dataPlugins.forEach { it.onPreInsertAdapterData(dataList, addedDataList, position) }
    }

    override fun onPostInsertAdapterData(dataList: List<T>, addedDataList: List<T>, position: Int) {
        dataPlugins.forEach { it.onPostInsertAdapterData(dataList, addedDataList, position) }
    }

    override fun onRemoveAdapterData(dataList: List<T>, removedData: T, position: Int) {
        dataPlugins.forEach { it.onRemoveAdapterData(dataList, removedData, position) }
    }

    override fun onMoveAdapterData(dataList: List<T>, movedData: T, fromIndex: Int, toIndex: Int) {
        dataPlugins.forEach { it.onMoveAdapterData(dataList, movedData, fromIndex, toIndex) }
    }

    override fun onReplaceAdapterData(dataList: List<T>, oldData: T, newData: T, replaceIndex: Int) {
        dataPlugins.forEach { it.onReplaceAdapterData(dataList, oldData, newData, replaceIndex) }
    }

    override fun onPreCreateViewHolder(parent: ViewGroup, viewType: Int) {
        viewHolderPlugins.forEach { it.onPreCreateViewHolder(parent, viewType) }
    }

    override fun onPostCreateViewHolder(parent: ViewGroup, viewType: Int, holder: RecyclerView.ViewHolder, createStart: Long) {
        viewHolderPlugins.forEach { it.onPostCreateViewHolder(parent, viewType, holder, createStart) }
    }

    override fun onPreBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        viewHolderPlugins.forEach { it.onPreBindViewHolder(holder, position) }
    }

    override fun onPostBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, bindStart: Long, data: T) {
        viewHolderPlugins.forEach { it.onPostBindViewHolder(holder, position, bindStart, data) }
    }

    override fun onDataScrolledOut(data: T) {
        lifecyclePlugins.forEach { it.onDataScrolledOut(data) }
    }

    override fun onDataScrolledIn(data: T) {
        lifecyclePlugins.forEach { it.onDataScrolledIn(data) }
    }

    override fun onAdapterScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        lifecyclePlugins.forEach { it.onAdapterScrollStateChanged(recyclerView, newState) }
    }

    override fun onAdapterScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        lifecyclePlugins.forEach { it.onAdapterScrolled(recyclerView, dx, dy) }
    }

    override fun onAdapterScrolledToTop(recyclerView: RecyclerView) {
        lifecyclePlugins.forEach { it.onAdapterScrolledToTop(recyclerView) }
    }

    override fun onAdapterScrolledToBottom(recyclerView: RecyclerView) {
        lifecyclePlugins.forEach { it.onAdapterScrolledToBottom(recyclerView) }
    }

    override fun onAdapterPageShow() {
        lifecyclePlugins.forEach { it.onAdapterPageShow() }
    }

    override fun onAdapterPageHide() {
        lifecyclePlugins.forEach { it.onAdapterPageHide() }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        lifecyclePlugins.forEach { it.onAttachedToRecyclerView(recyclerView) }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        lifecyclePlugins.forEach { it.onDetachedFromRecyclerView(recyclerView) }
    }

    override fun onListWriteBack(data: WriteBackData, adapter: BaseAdapter<T, *>) {
        writeBackPlugins.forEach { it.onListWriteBack(data, adapter) }
    }
}
