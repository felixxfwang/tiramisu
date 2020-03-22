package org.tiramisu.feeds.holder

import android.view.View
import org.tiramisu.feeds.behavior.IListLifecycleBehavior
import org.tiramisu.feeds.data.BaseAdapterData

abstract class BaseFeedViewHolder<T : BaseAdapterData>(itemView: View) : BaseViewHolder<T>(itemView) {

    protected fun <T : View> findView(viewId: Int): T {
        return itemView.findViewOften(viewId)
    }

    protected fun bindViewClick(id: Int){
        bindViewClick(findView<View>(id))
    }

    protected fun bindViewLongClick(id: Int){
        bindViewLongClick(findView<View>(id))
    }

    override fun onRegisterLifecycleBehavior(behaviors: MutableList<IListLifecycleBehavior>?) {
        super.onRegisterLifecycleBehavior(behaviors)

    }
}