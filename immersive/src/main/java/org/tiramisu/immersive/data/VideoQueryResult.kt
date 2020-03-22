package org.tiramisu.immersive.data

data class VideoQueryResult(
    val is_end: Boolean,
    val videos: List<Video>
) {
    override fun toString(): String {
        return "VideoQueryResult(is_end=$is_end, videos=${videos.size})"
    }
}