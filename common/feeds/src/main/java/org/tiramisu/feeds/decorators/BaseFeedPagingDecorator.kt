package org.tiramisu.feeds.decorators

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.tiramisu.feeds.adapter.decorator.AdapterLifecycleDecorator
import org.tiramisu.feeds.data.BaseAdapterData
import org.tiramisu.log.TLog

/**
 * 让RecyclerView分页自动加载数据
 */
abstract class BaseFeedPagingDecorator<T : BaseAdapterData>(
    private var preloadDistance: Int = 10
) : AdapterLifecycleDecorator<T> {

    companion object {
        private const val TAG = "PagingScrollListener"
    }

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
                isLoadingMore() -> { } // LogUtil.d(TAG, "source is loading, can't load next page.")
                isLastPage() -> TLog.d(TAG, "source is last page, can't load next page.")
                else -> {
                    TLog.d(TAG, "start load next page.")
                    onLoadMore()
                }
            }
        }
    }

    protected abstract fun isLoadingMore(): Boolean

    protected abstract fun isLastPage(): Boolean

    protected abstract fun onLoadMore()
}