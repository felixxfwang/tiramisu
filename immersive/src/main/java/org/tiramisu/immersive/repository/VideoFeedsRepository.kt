package org.tiramisu.immersive.repository

import kotlinx.coroutines.CoroutineScope
import org.tiramisu.feeds.repository.FeedReqParameter
import org.tiramisu.feeds.repository.FeedsCoroutineRepository
import org.tiramisu.immersive.data.VideoData
import org.tiramisu.immersive.data.VideoQueryParam
import org.tiramisu.immersive.data.VideoQueryResult
import org.tiramisu.repository.DataClient
import org.tiramisu.repository.http.HttpDataClient

class VideoFeedsRepository(scope: CoroutineScope)
    : FeedsCoroutineRepository<FeedReqParameter, List<VideoData>, VideoQueryParam, VideoQueryResult, Int>(scope) {

    override fun getRequest(param: FeedReqParameter, isLoadInitial: Boolean): VideoQueryParam {
        val page = if (isLoadInitial) 0 else nextKey ?: 0
        return VideoQueryParam(page, param.pageSize)
    }

    override fun getDataListFromRsp(param: FeedReqParameter, rsp: VideoQueryResult, isLoadInitial: Boolean): List<VideoData> {
        return rsp.videos.map { VideoData(it) }
    }

    override fun getNextKeyFromRsp(req: VideoQueryParam, rsp: VideoQueryResult): Int {
        return req.page + 1
    }

    override fun isLastPage(rsp: VideoQueryResult): Boolean = rsp.is_end

    override fun getDataClient(): DataClient<VideoQueryParam, VideoQueryResult> {
        return HttpDataClient(BASE_HTTP_URL, "videos", VideoQueryResult::class.java)
    }

}