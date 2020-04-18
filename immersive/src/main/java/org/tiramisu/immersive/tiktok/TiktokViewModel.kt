package org.tiramisu.immersive.tiktok

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.tiramisu.feeds.decorators.FeedPagingDecorator
import org.tiramisu.feeds.repository.FeedReqParameter
import org.tiramisu.feeds.repository.LoadInitialCallback
import org.tiramisu.feeds.repository.LoadMoreCallback
import org.tiramisu.immersive.data.VideoData
import org.tiramisu.immersive.repository.VideoFeedsSource

class TiktokViewModel : ViewModel(),
    LoadInitialCallback<FeedReqParameter, List<VideoData>>,
    LoadMoreCallback<FeedReqParameter, List<VideoData>> {

    private val repository = VideoFeedsSource(viewModelScope)
    private val req = FeedReqParameter()

    val adapter by lazy {
        TiktokAdapter().apply {
            addDecorator(FeedPagingDecorator(repository, req, 5, this@TiktokViewModel))
        }
    }

    fun loadInitial() {
        repository.loadInitial(req, this)
    }

    override fun onLoadDataSuccess(param: FeedReqParameter, data: List<VideoData>) {
        adapter.setAdapterData(data)
    }

    override fun onLoadMoreSuccess(param: FeedReqParameter, data: List<VideoData>, isLastPage: Boolean) {
        adapter.addAdapterData(data)
    }

    override fun onCleared() {

    }

}