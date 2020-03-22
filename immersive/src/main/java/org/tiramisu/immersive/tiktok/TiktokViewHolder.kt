package org.tiramisu.immersive.tiktok

import android.view.View
import android.widget.ImageView
import org.tiramisu.feeds.holder.BaseFeedViewHolder
import org.tiramisu.image.with
import org.tiramisu.immersive.R
import org.tiramisu.immersive.data.VideoData
import org.tiramisu.player.TMVideoView

class TiktokViewHolder(itemView: View) : BaseFeedViewHolder<VideoData>(itemView) {

    override fun onBindData(data: VideoData) {
        findView<ImageView>(R.id.img_thumb).with(context).load(data.video.cover_url)
        findView<TMVideoView>(R.id.video_view).setUp(data.video.video_url)
    }

}