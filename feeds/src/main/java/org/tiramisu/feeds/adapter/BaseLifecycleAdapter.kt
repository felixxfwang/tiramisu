package org.tiramisu.feeds.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.tiramisu.feeds.data.BaseAdapterData
import org.tiramisu.feeds.data.IChannelModel
import org.tiramisu.feeds.data.WriteBackData
import org.tiramisu.feeds.holder.AbstractViewHolder
import org.tiramisu.feeds.holder.BaseViewHolder
import org.tiramisu.feeds.holder.ViewHolderCreator
import org.tiramisu.feeds.holder.LifecycleViewHolder
import org.tiramisu.feeds.lifecycle.IListViewLifecycle
import org.tiramisu.feeds.lifecycle.IListWriteBackHandler
import org.tiramisu.feeds.plugins.FeedWriteBackDecorator
import org.tiramisu.log.TLog

/**
 *
 * @author felixxfwang
 * @date   2019-09-12
 */
open class BaseLifecycleAdapter<T : BaseAdapterData, VH: AbstractViewHolder>
    : BaseAdapter<T, VH>(), IListViewLifecycle, IListWriteBackHandler {

    companion object {
        private const val TAG = "BaseLifecycleAdapter"
    }

    var recyclerView: RecyclerView? = null
        private set

    private var channelPage: IChannelModel? = null

    init {
        addDecorator(FeedWriteBackDecorator())
    }

    override fun bindChannelData(channelPage: IChannelModel) {
        this.channelPage = channelPage
    }

    override fun getChannelData(): IChannelModel? = channelPage

    private val lifecycleScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            // 分发生命周期到ViewHolder
            IListViewLifecycle.Dispatcher.dispatchOnListScrollStateChanged(recyclerView, channelPage?.getChannelName(), newState)

            // 判断滑动到顶到底并分发到interceptors
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                // 判断是否滑动到顶
                if (!recyclerView.canScrollVertically(-1)) {
                    onListScrolledToTop(recyclerView)
                }
                // 判断是否滑动到底
                if (!recyclerView.canScrollVertically(1)) {
                    onListScrolledToBottom(recyclerView)
                }
            }

            // 分发到interceptors
            plugins.onAdapterScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            IListViewLifecycle.Dispatcher.dispatchOnListScrolled(recyclerView, channelPage?.getChannelName(), dx, dy)

            // 分发到interceptors
            plugins.onAdapterScrolled(recyclerView, dx, dy)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val createStart = System.currentTimeMillis()
        plugins.onPreCreateViewHolder(parent, viewType)

        val holder = ViewHolderCreator.create(parent, viewType)

        plugins.onPostCreateViewHolder(parent, viewType, holder, createStart)
        return holder as VH
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        val bindStart = System.currentTimeMillis()
        //插件貌似不需要使用到payloads参数，就先不改它
        plugins.onPreBindViewHolder(holder, position)

        val vh = holder as? BaseViewHolder<T>
        vh?.setChannelData(channelPage)
        vh?.checkAndBindData(getItem(position), position, payloads) // 这个方法里面有try catch 所以可以传null

        plugins.onPostBindViewHolder(holder, position, bindStart, getItem(position))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        recyclerView.addOnScrollListener(lifecycleScrollListener)
        subscribeWriteBackEvent()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeOnScrollListener(lifecycleScrollListener)
        this.recyclerView = null
        unsubscribeWriteBackEvent()
        TLog.i(TAG, "onDetachedFromRecyclerView: ${javaClass.simpleName}")
    }

    /**
     * ViewHolder 生命周期，attach
     */
    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        if (holder is LifecycleViewHolder) {
            holder.notifyAttachedToWindow(recyclerView)
        }
    }

    /**
     * ViewHolder 生命周期，detach
     */
    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        if (holder is LifecycleViewHolder) {
            holder.notifyDetachedFromWindow(recyclerView)
        }
    }

    // 数据回写相关
    override fun onListWriteBack(data: WriteBackData) {
        plugins.onListWriteBack(data, this)
    }

    private fun subscribeWriteBackEvent() {
//        if (subscriber == null) {
//            subscriber = RxBus.toFlowable(StateSyncEvent::class.java)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(WriteBackConsumer(this))
//        }
    }

//    class WriteBackConsumer(adapter: BaseLifecycleAdapter<*>): Consumer<StateSyncEvent> {
//        private var adapterRef = WeakReference(adapter)
//
//        override fun accept(it: StateSyncEvent) {
//            val data = ListWriteBackData(it.eventName, it.op, it.extra)
//            adapterRef.get()?.let { adapter ->
//                adapter.onListWriteBack(data)
//                BaseViewHolder.dispatchListWriteBack(adapter.recyclerView, data)
//            }
//        }
//    }

    private fun unsubscribeWriteBackEvent() {
//        subscriber?.dispose()
//        subscriber = null
    }

    protected fun onListScrolledToTop(recyclerView: RecyclerView) {
        plugins.onAdapterScrolledToTop(recyclerView)
    }

    protected fun onListScrolledToBottom(recyclerView: RecyclerView) {
        plugins.onAdapterScrolledToBottom(recyclerView)
    }

    override fun onListRefresh(list: RecyclerView?, channel: String?) {}

    override fun onListShow(list: RecyclerView?, channel: String?) {
        plugins.onAdapterPageShow()
    }

    override fun onListHide(list: RecyclerView?, channel: String?) {
        plugins.onAdapterPageHide()
    }

    override fun onListDestroy(list: RecyclerView?, channel: String?) {
        recyclerView = null
        channelPage = null
        unsubscribeWriteBackEvent()
        TLog.i(TAG, "onListDestroy: ${javaClass.simpleName}")
    }

    override fun onListScrolled(list: RecyclerView?, channel: String?, dx: Int, dy: Int) {}

    override fun onListScrollStateIdle(list: RecyclerView?, channel: String?) {}

    override fun onListScrollStateChanged(list: RecyclerView?, channel: String?, newState: Int) {}
}
