package org.tiramisu.feeds.adapter.plugin

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.tiramisu.feeds.data.BaseAdapterData

/**
 *
 * @author felixxfwang
 * @date   2019-10-12
 */
@SuppressWarnings("EmptyFunctionBlock")
interface AdapterViewHolderDecorator<T : BaseAdapterData> : BaseAdapterDecorator<T> {

    /**
     * 创建ViewHolder之前
     */
    fun onPreCreateViewHolder(parent: ViewGroup, viewType: Int) {}

    /**
     * 创建ViewHolder之后
     */
    fun onPostCreateViewHolder(parent: ViewGroup, viewType: Int, holder: RecyclerView.ViewHolder, createStart: Long) {}

    /**
     * bindViewHolder之前
     */
    fun onPreBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    /**
     * bindViewHolder之后
     */
    fun onPostBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, bindStart: Long, data: T) {}

}
