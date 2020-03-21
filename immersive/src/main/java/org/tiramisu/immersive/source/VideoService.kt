package org.tiramisu.immersive.source

import org.tiramisu.network.service.HttpCallback
import org.tiramisu.network.service.TiramisuHttp

object VideoService {

    private val http = TiramisuHttp().baseUrl("http://192.168.1.105:5000/")

    fun getVideos(callback: HttpCallback<VideoQueryResult>?) {
        http.get("videos", callback = callback)
    }
}