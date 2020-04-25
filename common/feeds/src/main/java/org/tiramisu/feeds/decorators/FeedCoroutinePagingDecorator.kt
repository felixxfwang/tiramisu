package org.tiramisu.feeds.decorators

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.tiramisu.feeds.data.BaseAdapterData
import org.tiramisu.feeds.repository.PagingCoroutineDataSource
import org.tiramisu.repository.DataResult
import java.util.concurrent.atomic.AtomicBoolean


interface DataLoadCallback<P, D> {
    fun onDataLoaded(param: P, result: DataResult<D>, isLastPage: Boolean)
}

class FeedCoroutinePagingDecorator<T : BaseAdapterData, P, D>(
    private val scope: CoroutineScope,
    private var source: PagingCoroutineDataSource<P, D>,
    private var paramFactory: () -> P,
    private var loadCallback: DataLoadCallback<P, D>,
    preloadDistance: Int = 10
) : BaseFeedPagingDecorator<T>(preloadDistance) {

    companion object {
        private const val TAG = "FeedCoroutinePagingDecorator"
    }

    private val isLoading = AtomicBoolean(false)

    override fun isLoadingMore(): Boolean = isLoading.get()

    override fun isLastPage(): Boolean = source.isLastPage()

    override fun onLoadMore() {
        isLoading.set(true)
        scope.launch {
            val param = paramFactory.invoke()
            val result = source.loadAfter(param)
            isLoading.set(false)
            loadCallback.onDataLoaded(param, result, isLastPage())
        }
    }
}