package org.tiramisu.feeds.decorators

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.tiramisu.feeds.adapter.decorator.AdapterLifecycleDecorator
import org.tiramisu.feeds.data.BaseAdapterData
import org.tiramisu.feeds.repository.LoadMoreCallback
import org.tiramisu.feeds.repository.PagingDataSource
import org.tiramisu.log.TLog

/**
 * 让RecyclerView分页自动加载数据
 *
 * @author felixxfwang
 * @date   2019-09-16
 */
class FeedPagingDecorator<T : BaseAdapterData, P, D>(
    private var preloadDistance: Int = 10,
    private var loadCallback: LoadMoreCallback<P, D>
) : AdapterLifecycleDecorator<T> {

    companion object {
        private const val TAG = "PagingScrollListener"
    }

    private var sourceFactory: DataSourceFactory<P, D>? = null
    private var source: PagingDataSource<P, D>? = null
    private var paramFactory: (() -> P)? = null
    private var param: P? = null

    constructor(
        repFactory: DataSourceFactory<P, D>,
        paramFactory: () -> P,
        preloadDistance: Int = 10,
        loadCallback: LoadMoreCallback<P, D>
    ): this(preloadDistance, loadCallback) {
        this.sourceFactory = repFactory
        this.paramFactory = paramFactory
    }

    constructor(
        repository: PagingDataSource<P, D>,
        param: P,
        preloadDistance: Int = 10,
        loadCallback: LoadMoreCallback<P, D>
    ): this(preloadDistance, loadCallback) {
        this.source = repository
        this.param = param
    }

    private fun getParameter() = param ?: paramFactory?.invoke()

    private fun dataSource() = source ?: sourceFactory?.invoke()

    override fun onAdapterScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            checkLoadMore(recyclerView)
        }
    }

    override fun onAdapterScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) { // 向上滑动。
            checkLoadMore(recyclerView)
        }
    }

    private fun checkLoadMore(recyclerView: RecyclerView) {
        val lastVisiblePosition: Int = when (val lm = recyclerView.layoutManager) {
            is LinearLayoutManager -> lm.findLastVisibleItemPosition()
            is StaggeredGridLayoutManager -> lm.findLastVisibleItemPositions(null).max() ?: 0
            else -> 0
        }

        val itemCount = recyclerView.adapter?.itemCount ?: 0

        if (lastVisiblePosition + preloadDistance >= itemCount) {
            when {
                dataSource()?.isLoadingAfter() == true -> { } // LogUtil.d(TAG, "source is loading, can't load next page.")
                dataSource()?.isLastPage() == true -> TLog.d(TAG, "source is last page, can't load next page.")
                else -> {
                    TLog.d(TAG, "start load next page.")
                    getParameter()?.let { param ->
                        dataSource()?.loadAfter(param, loadCallback)
                    }
                }
            }
        }
    }
}

typealias DataSourceFactory<P, D> = () -> PagingDataSource<P, D>
