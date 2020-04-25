package org.tiramisu.immersive.repository

import org.tiramisu.biz.base.BASE_HTTP_URL
import org.tiramisu.feeds.repository.FeedReqParameter
import org.tiramisu.feeds.repository.FeedsCoroutineSource
import org.tiramisu.immersive.data.PostData
import org.tiramisu.immersive.data.PostQueryParam
import org.tiramisu.immersive.data.PostQueryResult
import org.tiramisu.repository.coroutine.CoroutineDataClient
import org.tiramisu.repository.http.HttpCoroutineDataClient

class VideoFeedsSource : FeedsCoroutineSource<FeedReqParameter, List<PostData>, PostQueryParam, PostQueryResult, Int>() {

    override fun getRequest(param: FeedReqParameter, isLoadInitial: Boolean): PostQueryParam {
        val page = if (isLoadInitial) 0 else nextKey ?: 0
        return PostQueryParam(page, param.pageSize)
    }

    override fun getNextKeyFromRsp(req: PostQueryParam, rsp: PostQueryResult): Int {
        return req.page + 1
    }

    override fun isLastPage(rsp: PostQueryResult): Boolean = rsp.is_end

    override fun getDataClient(): CoroutineDataClient<PostQueryParam, PostQueryResult> {
        return HttpCoroutineDataClient(BASE_HTTP_URL, "posts", PostQueryResult::class.java)
    }

    override fun getResponse(
        param: FeedReqParameter,
        rsp: PostQueryResult,
        isLoadInitial: Boolean
    ): List<PostData> {
        return rsp.posts.map { PostData(it) }
    }
}