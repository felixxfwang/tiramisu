package org.tiramisu.feeds.adapter.decorator

import org.tiramisu.feeds.data.BaseAdapterData

/**
 *
 * @author felixxfwang
 * @date   2019-09-16
 */
interface AdapterDecorator<T : BaseAdapterData> : BaseAdapterDecorator<T>,
        AdapterDataDecorator<T>,
        AdapterLifecycleDecorator<T>,
        AdapterWriteBackDecorator<T>,
        AdapterViewHolderDecorator<T>
