package org.tiramisu.feeds.lifecycle;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 列表 ViewHolder 生命周期
 */
public interface IListViewHolderLifecycle {

    /**
     * ViewHolder attach到屏幕
     */
    void onAttachedToWindow(RecyclerView.ViewHolder viewHolder);

    /**
     * 曝光，在屏幕上变得可见
     */
    void onExposure(RecyclerView list, RecyclerView.ViewHolder viewHolder);

    /**
     * ViewHolder 底边在屏幕中，且列表idle时进行回调；
     */
    void onBottomIdleInScreen(RecyclerView.ViewHolder viewHolder, String channel, int top, int bottom, int containerTop, int containerBottom);

    /**
     * ViewHolder 完全显示在屏幕中，且列表idle时进行回调；
     */
    void onFullIdleInScreen(RecyclerView.ViewHolder viewHolder, String channel, int top, int bottom, int containerTop, int containerBottom);

    /**
     * ViewHolder在屏幕上变得不可见，与Exposure对立
     */
    void onDismiss(RecyclerView.ViewHolder viewHolder);

    /**
     * 列表滑动中，导致 ViewHolder 从屏幕 detach
     */
    void onScrollDetachedFromWindow(RecyclerView.ViewHolder viewHolder);

    /**
     * ViewHolder 从屏幕 detach
     */
    void onDetachedFromWindow(RecyclerView.ViewHolder viewHolder);
}
