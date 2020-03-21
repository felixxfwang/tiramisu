package org.tiramisu.feeds.lifecycle

import org.tiramisu.feeds.data.WriteBackData

interface IListWriteBackHandler {

    /**
     * 接收到列表项数据刷新的回调
     */
    fun onListWriteBack(data: WriteBackData)
}