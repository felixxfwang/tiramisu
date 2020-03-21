package org.tiramisu.immersive.data

data class VideoQueryResult(
    val is_end: Boolean,
    val videos: List<Video>
)