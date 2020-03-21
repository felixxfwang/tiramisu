package org.tiramisu.immersive.data

import org.tiramisu.http.HttpParam

class VideoQueryParam(var page: Int, val count: Int = 10) : HttpParam() {

    override fun toMap(): Map<String, Any> {
        return mapOf("page" to page, "count" to count)
    }
}