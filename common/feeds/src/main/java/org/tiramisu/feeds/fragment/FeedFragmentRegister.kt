package org.tiramisu.feeds.fragment

import androidx.fragment.app.Fragment

/**
 *
 * @author felixxfwang
 * @date   2019-10-14
 */
object FeedFragmentRegister : IFeedFragmentCreator {

    private val fragmentCreatorList = ArrayList<IFeedFragmentCreator>()

    /**
     * 注册IFragmentCreator
     * @param creator IFragmentCreator
     */
    fun registerFragmentCreator(creator: IFeedFragmentCreator) {
        if (!fragmentCreatorList.contains(creator)) {
            fragmentCreatorList.add(creator)
        }
    }

    /**
     * 反注册IFragmentCreator
     * @param creator IFragmentCreator
     */
    fun unregisterFragmentCreator(creator: IFeedFragmentCreator) {
        fragmentCreatorList.remove(creator)
    }

    override fun createFragment(channelType: Int): Fragment? {
        fragmentCreatorList.forEach { creator ->
            val fragment = creator.createFragment(channelType)
            if (fragment != null) {
                return fragment
            }
        }
        return null
    }
}
