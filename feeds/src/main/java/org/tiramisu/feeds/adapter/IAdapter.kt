package org.tiramisu.feeds.adapter

import org.tiramisu.feeds.data.BaseAdapterData

/**
 *
 * @author felixxfwang
 * @date   2019-10-11
 */
interface IAdapter<T: BaseAdapterData> : IPluggableAdapter<T>, ILifecycleAdapter, IRecycleViewAdapter<T> {

    /**
     * 设置Adapter数据
     */
    fun setAdapterData(data: List<T>) {}

    /**
     * 新增Adapter数据。
     */
    fun addAdapterData(data: List<T>, notifyUI: Boolean = true) { }

    /**
     * 将数据添加到列表末尾
     */
    fun addAdapterData(data: T, notifyUI: Boolean = true) {}

    /**
     * 替换Adapter指定位置数据
     */
    fun replaceAdapterData(position: Int, data: T, notifyUI: Boolean = true) {}

    /**
     * 插入某个Item数据。
     */
    fun insertAdapterData(position: Int, data: T, notifyUI: Boolean = true) {}

    /**
     * 在某个位置插入多个数据
     */
    fun insertAdapterData(position: Int, dataList: List<T>) {}

    /**
     *  删除某个item数据
     */
    fun removeAdapterData(position: Int, notifyUI: Boolean = true) {}

    /**
     * 删除某个item数据
     */
    fun removeAdapterData(data: T, notifyUI: Boolean = true): Boolean = false

    /**
     * 将某个位置的数据移动到另一个位置
     */
    fun moveAdapterData(fromIndex: Int, toIndex: Int) {}

    /**
     * 删除整个数据列表
     */
    fun removeAllAdapterData() {}
}
