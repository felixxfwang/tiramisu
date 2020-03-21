package org.tiramisu.immersive

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.tiramisu.biz.base.DataConstants
import org.tiramisu.feeds.holder.BaseViewHolder
import org.tiramisu.feeds.holder.IViewHolderCreator

class TiktokViewHolderCreator : IViewHolderCreator{

    override fun createViewHolder(context: Context?, parent: ViewGroup, viewType: Int): BaseViewHolder<*>? {
        return when (viewType) {
            DataConstants.DATA_TIKTOK_VIDEO -> TiktokViewHolder(createView(R.layout.item_immersive_video, parent))
            else -> null
        }
    }

    private fun createView(layoutId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }
}