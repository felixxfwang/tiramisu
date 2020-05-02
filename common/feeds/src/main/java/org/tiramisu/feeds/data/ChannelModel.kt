package org.tiramisu.feeds.data

class ChannelModel(
    private val name: String,
    private val type: Int,
    private val pageId: String = ""
) : IChannelModel {
    override fun getPageId(): String = pageId

    override fun getChannelName(): String = name

    override fun getChannelType(): Int = type

    override fun <T> getChannelData(): T? = null

}