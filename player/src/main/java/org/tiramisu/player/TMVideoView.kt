package org.tiramisu.player

import android.content.Context
import android.util.AttributeSet
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd

class TMVideoView(context: Context, attrs: AttributeSet? = null) : JzvdStd(context, attrs), IVideoView {

    override fun start() {
        startVideo()
    }

    override fun pause() {
        if (state == Jzvd.STATE_PLAYING) {
            mediaInterface.pause()
            onStatePause()
        }
    }

    override fun resume() {
        if (state == Jzvd.STATE_PAUSE) {
            mediaInterface.start()
            onStatePlaying()
        }
    }

    override fun isPlaying() : Boolean {
        return mediaInterface.isPlaying
    }
}