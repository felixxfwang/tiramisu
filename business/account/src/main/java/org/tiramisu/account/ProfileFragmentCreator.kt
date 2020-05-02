package org.tiramisu.account

import androidx.fragment.app.Fragment
import org.tiramisu.biz.base.DataConstants
import org.tiramisu.feeds.fragment.IFeedFragmentCreator

class ProfileFragmentCreator : IFeedFragmentCreator {
    override fun createFragment(channelType: Int): Fragment? {
        return when (channelType) {
            DataConstants.CHANNEL_PROFILE -> ProfileChannelFragment()
            else -> null
        }
    }
}