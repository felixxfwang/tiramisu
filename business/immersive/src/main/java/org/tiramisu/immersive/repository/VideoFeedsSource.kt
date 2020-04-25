package org.tiramisu.immersive.repository

import org.tiramisu.biz.base.BASE_HTTP_URL
import org.tiramisu.feeds.repository.FeedReqParameter
import org.tiramisu.feeds.repository.FeedsCoroutineSource
import org.tiramisu.immersive.data.VideoData
import org.tiramisu.immersive.data.VideoQueryParam
import org.tiramisu.immersive.data.VideoQueryResult
import org.tiramisu.repository.coroutine.CoroutineDataClient
import org.tiramisu.repository.http.HttpCoroutineDataClient

class VideoFeedsSource : FeedsCoroutineSource<FeedReqParameter, List<VideoData>, VideoQueryParam, VideoQueryResult, Int>() {

    override fun getRequest(param: FeedReqParameter, isLoadInitial: Boolean): VideoQueryParam {
        val page = if (isLoadInitial) 0 else nextKey ?: 0
        return VideoQueryParam(page, param.pageSize)
    }

    override fun getNextKeyFromRsp(req: VideoQueryParam, rsp: VideoQueryResult): Int {
        return req.page + 1
    }

    override fun isLastPage(rsp: VideoQueryResult): Boolean = rsp.is_end

    override fun getDataClient(): CoroutineDataClient<VideoQueryParam, VideoQueryResult> {
        return HttpCoroutineDataClient(BASE_HTTP_URL, "videos", VideoQueryResult::class.java)
    }

    override fun getResponse(
        param: FeedReqParameter,
        rsp: VideoQueryResult,
        isLoadInitial: Boolean
    ): List<VideoData> {
        return rsp.videos.map { VideoData(it) }
    }
}