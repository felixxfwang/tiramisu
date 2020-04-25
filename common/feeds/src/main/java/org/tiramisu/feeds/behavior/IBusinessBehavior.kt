package org.tiramisu.feeds.behavior

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface IBusinessBehavior {

    /**
     * 整个itemView被点击
     */
    fun onItemViewClick(
        view: View,
        viewHolder: RecyclerView.ViewHolder
    ) {}

    /**
     * 整个itemView被长按点击
     */
    fun onItemViewLongClick(
        view: View,
        viewHolder: RecyclerView.ViewHolder
    ) {}

    /**
     * 具体的某一个视图被点击
     * @param view 被点击的视图
     * @param viewHolder 被点击的视图所在的ViewHolder
     */
    fun onViewClick(
        view: View,
        viewHolder: RecyclerView.ViewHolder
    ) {}

    /**
     * 具体的某个子视图被长按点击
     */
    fun onViewLongClick(
        view: View,
        viewHolder: RecyclerView.ViewHolder
    ) {}
}