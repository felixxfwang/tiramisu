package org.tiramisu.feeds.fragment

import android.util.SparseArray
import androidx.core.util.set
import androidx.fragment.app.Fragment
import org.tiramisu.feeds.bindChannel
import org.tiramisu.feeds.data.IChannelModel
import org.tiramisu.log.TLog

/**
 * 这是一个最简单的fragment复用池子，fragment的类型由外部控制
 */
class FeedFragmentPool {

    companion object {
        private const val TAG = "FragmentPoolManager"
    }

    //可用的fragment，key为fragment类型
    private var mRecycleFragment = SparseArray<MutableList<Fragment>>()
    /**
     * 根据类型获取可用的fragment，若没有返回null
     */
    fun getFragment(channel: IChannelModel): Fragment? {
        val list = mRecycleFragment[channel.getChannelType()]
        return if (!list.isNullOrEmpty()) {
            val value = list.removeAt(0)
            TLog.d(TAG, "${value.hashCode()} remove to cache")
            value.bindChannel(channel, true)
        } else {
            FeedFragmentRegister.createFragment(channel.getChannelType())?.bindChannel(channel)
        }
    }

    /**
     * 给复用池提供fragment
     * @param fragment的类型
     */
    fun recycleFragment(fragment: Fragment, type: Int){
        TLog.d(TAG, "${fragment.hashCode()} add to cache")
        var list = mRecycleFragment[type]
        if(list == null){
            list = ArrayList()
            mRecycleFragment[type] = list
        }
        list.add(fragment)
    }

    /**
     * 全部清除
     */
    fun clear() {
        mRecycleFragment.clear()
    }

}