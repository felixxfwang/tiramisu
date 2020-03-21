package org.tiramisu.feeds.plugins

import org.tiramisu.feeds.adapter.BaseAdapter
import org.tiramisu.feeds.adapter.plugin.AdapterWriteBackPlugin
import org.tiramisu.feeds.data.BaseAdapterData
import org.tiramisu.feeds.data.WriteBackData

/**
 *
 * @author felixxfwang
 * @date   2019-10-09
 */
class FeedWriteBackPlugin<T: BaseAdapterData> : AdapterWriteBackPlugin<T> {

    override fun onListWriteBack(data: WriteBackData, adapter: BaseAdapter<T, *>) {

    }
}
