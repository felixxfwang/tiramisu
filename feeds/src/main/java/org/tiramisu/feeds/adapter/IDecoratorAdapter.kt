package org.tiramisu.feeds.adapter

import org.tiramisu.feeds.adapter.plugin.BaseAdapterDecorator
import org.tiramisu.feeds.data.BaseAdapterData

/**
 *
 * @author felixxfwang
 * @date   2019-10-11
 */
interface IDecoratorAdapter<T : BaseAdapterData> {

    /**
     * 添加AdapterPlugin
     */
    fun addDecorator(decorator: BaseAdapterDecorator<T>) {}

    /**
     * 删除AdapterPlugin
     */
    fun removeDecorator(decorator: BaseAdapterDecorator<T>) {}
}
