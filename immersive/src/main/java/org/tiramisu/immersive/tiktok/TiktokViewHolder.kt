package org.tiramisu.immersive.tiktok

import android.view.View
import android.widget.ImageView
import org.tiramisu.feeds.holder.BaseFeedViewHolder
import org.tiramisu.image.with
import org.tiramisu.immersive.R
import org.tiramisu.immersive.data.VideoData
import org.tiramisu.player.TMVideoView

class TiktokViewHolder(itemView: View) : BaseFeedViewHolder<VideoData>(itemView) {

    private val videoView = findView<TMVideoView>(R.id.video_view)
    private val videoCover = findView<ImageView>(R.id.img_thumb)

    override fun onBindData(data: VideoData) {
        videoCover.with(context).load(data.video.cover_url)
        videoView.setUp(data.video.video_url)
    }

    fun play() {
        videoView.start()
        videoCover.visibility = View.INVISIBLE
    }

    fun pause() {
        videoView.pause()
//        videoCover.visibility = View.INVISIBLE
    }

}