package org.tiramisu.immersive.repository

import org.tiramisu.feeds.FeedReqParameter
import org.tiramisu.feeds.FeedsDataRepository
import org.tiramisu.immersive.data.Video
import org.tiramisu.immersive.data.VideoQueryParam
import org.tiramisu.immersive.data.VideoQueryResult
import org.tiramisu.repository.DataClient
import org.tiramisu.repository.http.HttpDataClient

class VideoFeedsRepository : FeedsDataRepository<FeedReqParameter, List<Video>, VideoQueryParam, VideoQueryResult, Int>() {

    override fun getRequest(param: FeedReqParameter, isLoadInitial: Boolean): VideoQueryParam {
        val page = if (isLoadInitial) 0 else nextKey ?: 0
        return VideoQueryParam(page, param.pageSize)
    }

    override fun getDataListFromRsp(param: FeedReqParameter, rsp: VideoQueryResult, isLoadInitial: Boolean): List<Video> {
        return rsp.videos
    }

    override fun getNextKeyFromRsp(req: VideoQueryParam, rsp: VideoQueryResult): Int {
        return req.page + 1
    }

    override fun isLastPage(rsp: VideoQueryResult): Boolean = rsp.is_end

    override fun getDataClient(): DataClient<VideoQueryParam, VideoQueryResult> {
        return HttpDataClient(BASE_HTTP_URL, "videos", VideoQueryResult::class.java)
    }

}