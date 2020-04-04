package org.tiramisu.feeds.fragment

import androidx.fragment.app.Fragment

/**
 *
 * @author felixxfwang
 * @date   2019-10-12
 */
interface IFeedFragmentCreator {

    /**
     * 根据频道类型创建Fragment
     */
    fun createFragment(channelType: Int): Fragment?
}
