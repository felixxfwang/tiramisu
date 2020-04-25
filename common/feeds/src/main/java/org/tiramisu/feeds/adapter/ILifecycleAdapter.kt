package org.tiramisu.feeds.adapter

import org.tiramisu.feeds.data.IChannelModel

/**
 *
 * @author felixxfwang
 * @date   2019-10-11
 */
interface ILifecycleAdapter {

    /**
     * 绑定页面相关数据
     */
    fun bindChannelData(channelPage: IChannelModel) {}

    /**
     * 对外暴露获得channelPage
     */
    fun getChannelData(): IChannelModel? = null
}
