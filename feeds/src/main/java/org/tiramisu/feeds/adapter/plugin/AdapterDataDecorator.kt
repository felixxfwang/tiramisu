package org.tiramisu.feeds.adapter.plugin

import org.tiramisu.feeds.data.BaseAdapterData

/**
 *
 * @author felixxfwang
 * @date   2019-10-12
 */
interface AdapterDataDecorator<T : BaseAdapterData> : BaseAdapterDecorator<T> {

    /**
     * 设置数据之前执行
     */
    fun onPreSetAdapterData(addedDataList: MutableList<T>) {}

    /**
     * 设置数据之后执行
     */
    fun onSetAdapterData(dataList: List<T>) {}

    /**
     * 添加数据之前执行
     */
    fun onAddAdapterData(dataList: List<T>, addedDataList: MutableList<T>) {}

    /**
     * 插入数据之前执行
     */
    fun onPreInsertAdapterData(dataList: List<T>, addedDataList: List<T>, position: Int) {}

    /**
     * 插入数据之后执行
     */
    fun onPostInsertAdapterData(dataList: List<T>, addedDataList: List<T>, position: Int) {}

    /**
     * 删除数据之后执行
     */
    fun onRemoveAdapterData(dataList: List<T>, removedData: T, position: Int) {}

    /**
     * 移动数据之后执行
     */
    fun onMoveAdapterData(dataList: List<T>, movedData: T, fromIndex: Int, toIndex: Int) {}

    /**
     * 替换数据之后执行
     */
    fun onReplaceAdapterData(dataList: List<T>, oldData: T, newData: T, replaceIndex: Int) {}
}
