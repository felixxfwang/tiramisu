package org.tiramisu.immersive.tiktok

import org.tiramisu.feeds.adapter.BaseLifecycleAdapter
import org.tiramisu.immersive.data.VideoData

class TiktokAdapter : BaseLifecycleAdapter<VideoData, TiktokViewHolder>() {

    companion object {
        private const val TAG = "TiktokAdapter"
    }

    override fun onViewAttachedToWindow(holder: TiktokViewHolder) {
        holder.videoView.start()
    }

    override fun onViewDetachedFromWindow(holder: TiktokViewHolder) {
        holder.videoView.pause()
        // 这里千万不能做动画，否则ItemView会处于Transient State，从而导致holder无法被复用
//        holder.thumbImage.animate().alpha(1f).start()
//        holder.playImage.animate().alpha(0f).start()
    }

}