package org.tiramisu.immersive

import android.net.Uri
import android.view.View
import android.widget.ImageView
import org.tiramisu.feeds.holder.BaseViewHolder
import org.tiramisu.immersive.data.VideoData
import org.tiramisu.log.TLog
import org.tiramisu.player.TMVideoView

class TiktokViewHolder(itemView: View) : BaseViewHolder<VideoData>(itemView) {
        companion object {
            private const val TAG = "TiktokViewHolder"
        }
        var thumbImage: ImageView = itemView.findViewById(R.id.img_thumb)
        var videoView: TMVideoView = itemView.findViewById(R.id.video_view)
        var playImage: ImageView = itemView.findViewById(R.id.img_play)

        override fun onBindData(data: VideoData) {
                TLog.i(TAG, "onBindViewHolder: position: $position, videoView: ${videoView.hashCode()}")
                thumbImage.setImageURI(Uri.parse(data.video.cover_url))
                videoView.setUp(data.video.video_url)
        }

}