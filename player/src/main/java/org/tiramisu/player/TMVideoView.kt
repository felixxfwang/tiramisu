package org.tiramisu.player

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd

class TMVideoView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs), IVideoView {

    companion object {

        fun pause() {
            Jzvd.releaseAllVideos()
        }

        fun back(): Boolean {
            return Jzvd.backPress()
        }
    }

    private val video = JzvdStd(context)

    init {
        addView(video)
    }

    override fun setUp(videoUrl: String) {
        video.setUp(videoUrl, "")
    }

    override fun start() {
        video.startVideo()
    }

    override fun pause() {
        if (video.state == Jzvd.STATE_PLAYING) {
            video.mediaInterface.pause()
            video.onStatePause()
        }
    }

    override fun resume() {
        if (video.state == Jzvd.STATE_PAUSE) {
            video.mediaInterface.start()
            video.onStatePlaying()
        }
    }

    override fun isPlaying() : Boolean {
        return video.mediaInterface.isPlaying
    }
}