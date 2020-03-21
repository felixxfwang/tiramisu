package org.tiramisu.feeds.behavior;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface IBusinessBehavior extends IBehavior {

    /**
     * 整个itemView被点击
     */
    void onItemViewClick(View view, RecyclerView.ViewHolder viewHolder);

    /**
     * 整个itemView被长按点击
     */
    void onItemViewLongClick(View view, RecyclerView.ViewHolder viewHolder);

    /**
     * 具体的某一个视图被点击
     * @param view 被点击的视图
     * @param viewHolder 被点击的视图所在的ViewHolder
     */
    void onViewClick(View view, RecyclerView.ViewHolder viewHolder);

    /**
     *具体的某个子视图被长按点击
     */
    void onViewLongClick(View view, RecyclerView.ViewHolder viewHolder);
}
