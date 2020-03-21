package org.tiramisu.feeds.adapter.plugin

import org.tiramisu.feeds.data.BaseAdapterData

/**
 *
 * @author felixxfwang
 * @date   2019-09-16
 */
interface AdapterPlugin<T : BaseAdapterData> : BaseAdapterPlugin<T>,
        AdapterDataPlugin<T>,
        AdapterLifecyclePlugin<T>,
        AdapterWriteBackPlugin<T>,
        AdapterViewHolderPlugin<T>
