package org.tiramisu.feeds.adapter

import org.tiramisu.feeds.data.BaseAdapterData

/**
 *
 * @author felixxfwang
 * @date   2019-10-21
 */
interface IRecycleViewAdapter<T : BaseAdapterData> {

    /**
     * 获取某个位置的数据项
     */
    fun getItem(position: Int) : T

    /**
     * 获取数据数量
     */
    fun getItemCount(): Int

    /**
     * 获取整个数据列表，这里返回值一定是List<T>类型，不允许外部对mData内容进行修改
     */
    fun getDataList(): List<T> = emptyList()
}
