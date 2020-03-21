package org.tiramisu.immersive.source

import org.tiramisu.network.service.HttpParam

class VideoHttpParam(var page: Int, val count: Int = 10) : HttpParam() {

    override fun toMap(): Map<String, Any> {
        return mapOf("page" to page, "count" to count)
    }
}