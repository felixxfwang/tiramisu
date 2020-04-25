package org.tiramisu.feeds

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.tiramisu.feeds.data.IChannelModel
import org.tiramisu.feeds.lifecycle.IListViewLifecycle
import org.tiramisu.log.TLog
import org.tiramisu.page.modular.IPageModuleManager
import org.tiramisu.page.modular.fragment.BaseFragmentModule
import org.tiramisu.page.modular.fragment.IFragmentModule

class FeedsLifecycleModule(
    private val recycler: RecyclerView
) : BaseFragmentModule<IPageModuleManager>() {

    companion object {
        private const val TAG = "FragmentListModule"

        const val KEY_RECYCLED = "fragment_is_recycled"
    }

    var channel: IChannelModel? = null
        private set

    // 保存视图，用于复用
    private var rootView: View? = null

    private fun handleArguments(arguments: Bundle?) {
        val args = arguments ?: return
        channel = args.getSerializable(IChannelModel.CHANNEL_KEY) as? IChannelModel
    }

    override fun onFragmentViewPreCreate(fragment: Fragment): View? {
        handleArguments(fragment.arguments)
        return rootView
    }

    override fun onFragmentViewCreated(fragment: Fragment, view: View) {
        rootView = view
        if (fragment.arguments?.getBoolean(KEY_RECYCLED) == true) {
            getBus<IFragmentModule>()?.onFragmentViewReused()
            TLog.i(TAG, "onPageViewReused.")

            fragment.arguments?.remove(KEY_RECYCLED)
        }
        getBus<IFragmentModule>()?.onFragmentViewRefresh()
    }

    // ------------------- Page生命周期 --------------------
    override fun onFragmentShow() {
        IListViewLifecycle.Dispatcher.dispatchOnListShow(recycler, channel?.getChannelName())
    }

    override fun onFragmentHide() {
        IListViewLifecycle.Dispatcher.dispatchOnListHide(recycler, channel?.getChannelName())
    }

    override fun onFragmentViewDestroyed() {
        IListViewLifecycle.Dispatcher.dispatchOnListDestroy(recycler, channel?.getChannelName())
    }
    // ------------------- Page生命周期 --------------------
}