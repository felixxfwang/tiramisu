package org.tiramisu.feeds.decorators

import org.tiramisu.feeds.data.BaseAdapterData
import org.tiramisu.feeds.repository.LoadMoreCallback
import org.tiramisu.feeds.repository.PagingDataSource

/**
 * 让RecyclerView分页自动加载数据
 *
 * @author felixxfwang
 * @date   2019-09-16
 */
class FeedPagingDecorator<T : BaseAdapterData, P, D>(
    private var source: PagingDataSource<P, D>,
    private var paramFactory: () -> P,
    private var loadCallback: LoadMoreCallback<P, D>,
    preloadDistance: Int = 10
) : BaseFeedPagingDecorator<T>(preloadDistance) {

    companion object {
        private const val TAG = "FeedPagingDecorator"
    }

    override fun isLoadingMore(): Boolean = source.isLoadingAfter()

    override fun isLastPage(): Boolean = source.isLastPage()

    override fun onLoadMore() {
        source.loadAfter(paramFactory.invoke(), loadCallback)
    }
}