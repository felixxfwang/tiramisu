package org.tiramisu.feeds.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.tiramisu.feeds.data.IChannelModel

/**
 *
 * @author felixxfwang
 * @date   2019-10-22
 */
abstract class ViewPagerFragmentStateAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentPool = FeedFragmentPool()

    /**
     * 获取指定位置的频道模型
     */
    abstract fun getChannelModel(position: Int): IChannelModel?

    override fun getItem(position: Int): Fragment {
        return getChannelModel(position)?.let { fragmentPool.getFragment(it) } ?: Fragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return getChannelModel(position)?.getChannelName()
    }
}
