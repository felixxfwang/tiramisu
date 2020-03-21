package org.tiramisu.player

interface IVideoView{

    fun setUp(videoUrl: String)

    fun start()

    fun pause()

    fun resume()

    fun isPlaying(): Boolean
}