package org.tiramisu.immersive.data

import org.tiramisu.biz.base.DataConstants
import org.tiramisu.feeds.data.BaseAdapterData

data class Video(
    val cover_url: String,
    val id: String,
    val video_duration: String,
    val video_title: String,
    val video_url: String
) : BaseAdapterData(DataConstants.DATA_TIKTOK_VIDEO)