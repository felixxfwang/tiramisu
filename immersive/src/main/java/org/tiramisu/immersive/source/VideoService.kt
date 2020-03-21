package org.tiramisu.immersive.source

import org.tiramisu.http.HttpCallback
import org.tiramisu.http.TiramisuHttp

typealias VideoQueryCallback = HttpCallback<VideoHttpParam, VideoQueryResult>

object VideoService {
    private val http = TiramisuHttp().baseUrl("http://192.168.1.105:5000/")

    fun getVideos(param: VideoHttpParam, callback: VideoQueryCallback? = null) {
        http.get("videos", param, callback = callback)
    }
}