package org.tiramisu.feeds.data

/**
 * 每个列表项数据抽象
 *
 * @author felixxfwang
 * @date   2019-10-08
 */
interface IAdapterData {

    /**
     * 获取页面ID，用于频道页面标识或者数据上报
     */
    fun pageId(): String

    /**
     * 获取数据类型
     */
    fun dataType(): Int

    /**
     * 获取展示类型
     */
    fun showType(): Int
}
