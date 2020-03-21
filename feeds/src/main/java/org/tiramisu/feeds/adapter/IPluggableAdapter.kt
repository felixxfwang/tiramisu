package org.tiramisu.feeds.adapter

import org.tiramisu.feeds.adapter.plugin.BaseAdapterPlugin
import org.tiramisu.feeds.data.BaseAdapterData

/**
 *
 * @author felixxfwang
 * @date   2019-10-11
 */
interface IPluggableAdapter<T : BaseAdapterData> {

    /**
     * 添加AdapterPlugin
     */
    fun addAdapterPlugin(plugin: BaseAdapterPlugin<T>) {}

    /**
     * 删除AdapterPlugin
     */
    fun removeAdapterPlugin(plugin: BaseAdapterPlugin<T>) {}
}
