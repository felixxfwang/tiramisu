package org.tiramisu.immersive.repository

import org.tiramisu.http.HttpCallback
import org.tiramisu.immersive.data.VideoQueryParam
import org.tiramisu.immersive.data.VideoQueryResult

typealias VideoQueryCallback = HttpCallback<VideoQueryParam, VideoQueryResult>

const val BASE_HTTP_URL = "http://192.168.1.105:5000/"