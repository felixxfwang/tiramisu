package org.tiramisu.player

interface IVideoView{

    fun start()

    fun pause()

    fun resume()

    fun isPlaying(): Boolean
}