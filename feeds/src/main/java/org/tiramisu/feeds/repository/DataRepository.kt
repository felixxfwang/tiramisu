package org.tiramisu.feeds.repository

import org.tiramisu.repository.DataRepository
import org.tiramisu.repository.LoadCallback
import org.tiramisu.repository.LoadDataCallback

/**
 * 分页数据源
 */
interface PagingDataRepository<P, D> : DataRepository<P, D> {
    /**
     * 刷新页面
     */
    fun loadInitial(param: P, callback: LoadInitialCallback<P, D>?)

    /**
     * 加载更多
     */
    fun loadAfter(param: P, callback: LoadMoreCallback<P, D>?)
    fun isLastPage(): Boolean
    fun isLoadingInitial(): Boolean
    fun isLoadingAfter(): Boolean
}

typealias LoadInitialCallback<P, D> = LoadDataCallback<P, D>

/**
 * 数据刷新回调
 */
interface LoadMoreCallback<P, T> : LoadCallback<P, T> {

    /**
     * 开始加载更多请求的回调
     */
    fun onLoadMoreStart(param: P) {}

    /**
     * 加载更多请求成功的回调
     * @param isLastPage 是否是最后一页，如果是就不需要再加载更多了
     */
    fun onLoadMoreSuccess(param: P, data: T, isLastPage: Boolean)

    /**
     * 加载更多请求失败的回调
     */
    fun onLoadMoreFailed(param: P, errorCode: Int, errorMsg: String?) {}

    /**
     * 加载更多请求完成的回调
     */
    fun onLoadMoreComplete(param: P) {}
}
