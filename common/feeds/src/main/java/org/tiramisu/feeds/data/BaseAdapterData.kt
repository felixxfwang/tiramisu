package org.tiramisu.feeds.data

open class BaseAdapterData(private val dataType: Int) : IAdapterData {
    override fun pageId(): String = ""

    override fun dataType(): Int = dataType

    override fun showType(): Int = dataType
}