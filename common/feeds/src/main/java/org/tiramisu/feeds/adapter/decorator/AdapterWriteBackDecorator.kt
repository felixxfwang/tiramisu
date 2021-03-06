package org.tiramisu.feeds.adapter.decorator

import org.tiramisu.feeds.adapter.BaseAdapter
import org.tiramisu.feeds.data.BaseAdapterData
import org.tiramisu.feeds.data.WriteBackData

/**
 *
 * @author felixxfwang
 * @date   2019-10-12
 */
@SuppressWarnings("EmptyFunctionBlock")
interface AdapterWriteBackDecorator<T : BaseAdapterData> : BaseAdapterDecorator<T> {
    /**
     * 数据刷新事件
     */
    fun onListWriteBack(data: WriteBackData, adapter: BaseAdapter<T, *>) {}
}
