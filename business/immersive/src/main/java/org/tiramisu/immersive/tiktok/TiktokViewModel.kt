package org.tiramisu.immersive.tiktok

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.tiramisu.feeds.decorators.DataLoadCallback
import org.tiramisu.feeds.decorators.FeedCoroutinePagingDecorator
import org.tiramisu.feeds.repository.FeedReqParameter
import org.tiramisu.immersive.data.PostData
import org.tiramisu.immersive.repository.VideoFeedsSource
import org.tiramisu.repository.DataResult

class TiktokViewModel : ViewModel(), DataLoadCallback<FeedReqParameter, List<PostData>> {

    private val source = VideoFeedsSource()
    private val req = FeedReqParameter()

    val adapter by lazy {
        TiktokAdapter().apply {
            addDecorator(FeedCoroutinePagingDecorator(viewModelScope, source, ::getFeedReq, this@TiktokViewModel, 5))
        }
    }

    private fun getFeedReq() = req

    fun loadInitial() {
        viewModelScope.launch {
            val result = source.loadInitial(req)
            if (result.isSuccess()) {
                adapter.setAdapterData(result.get())
            }
        }
    }

    override fun onDataLoaded(param: FeedReqParameter, result: DataResult<List<PostData>>, isLastPage: Boolean) {
        if (result.isSuccess()) {
            adapter.addAdapterData(result.get())
        }
    }

    override fun onCleared() {

    }

}