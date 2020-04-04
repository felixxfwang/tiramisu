package org.tiramisu.feeds

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.tiramisu.feeds.data.IChannelModel
import org.tiramisu.page.modular.fragment.IFragmentModularPage

fun IFragmentModularPage.bindFeedsLifecycle(recyclerView: RecyclerView) {
    modular.addModule(FeedsLifecycleModule(recyclerView))
}

fun IFragmentModularPage.getFeedsChannel(): IChannelModel? {
    return modular.getModule<FeedsLifecycleModule>()?.channel
}

fun Fragment.bindChannel(channel: IChannelModel, isRecycled: Boolean = false): Fragment {
    val args = arguments ?: Bundle()
    args.putSerializable(IChannelModel.CHANNEL_KEY, channel)
    args.putBoolean(FeedsLifecycleModule.KEY_RECYCLED, isRecycled)
    arguments = args
    return this
}