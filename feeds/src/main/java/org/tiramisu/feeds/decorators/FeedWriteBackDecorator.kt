package org.tiramisu.feeds.decorators

import org.tiramisu.feeds.adapter.BaseAdapter
import org.tiramisu.feeds.adapter.decorator.AdapterWriteBackDecorator
import org.tiramisu.feeds.data.BaseAdapterData
import org.tiramisu.feeds.data.WriteBackData

/**
 *
 * @author felixxfwang
 * @date   2019-10-09
 */
class FeedWriteBackDecorator<T: BaseAdapterData> : AdapterWriteBackDecorator<T> {

    override fun onListWriteBack(data: WriteBackData, adapter: BaseAdapter<T, *>) {

    }
}
