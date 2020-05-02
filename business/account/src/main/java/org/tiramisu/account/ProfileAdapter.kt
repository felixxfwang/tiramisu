package org.tiramisu.account

import androidx.fragment.app.FragmentManager
import org.tiramisu.feeds.data.IChannelModel
import org.tiramisu.feeds.fragment.ViewPagerFragmentStateAdapter

class ProfileAdapter(fm: FragmentManager) : ViewPagerFragmentStateAdapter(fm) {

    private val channelModels = ArrayList<IChannelModel>()

    fun setChannels(channels: List<IChannelModel>) {
        this.channelModels.clear()
        this.channelModels.addAll(channels)
    }

    override fun getChannelModel(position: Int): IChannelModel? = channelModels.getOrNull(position)

    override fun getCount(): Int = channelModels.size
}