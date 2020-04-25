package org.tiramisu.feeds.data

import java.io.Serializable

/**
 * 多频道Feeds流列表中的频道数据抽象
 *
 * @author felixxfwang
 * @date   2019-10-08
 */
interface IChannelModel : Serializable {

    companion object {
        const val CHANNEL_KEY: String = "channel_key"
    }

    /**
     * 获取页面ID，用于频道页面标识或者数据上报
     */
    fun getPageId(): String

    /**
     * 频道名称
     */
    fun getChannelName(): String

    /**
     * 频道类型，用于复用
     */
    fun getChannelType(): Int

    /**
     * 频道数据
     */
    fun <T> getChannelData(): T?
}
