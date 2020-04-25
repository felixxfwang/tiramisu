package org.tiramisu.feeds.lifecycle;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.tiramisu.feeds.adapter.wrapper.BaseWrapperAdapter;

/**
 * 列表生命周期
 */
public interface IListViewLifecycle {

    /**
     * 列表刷新时触发
     */
    void onListRefresh(RecyclerView list, String channel);

    /**
     * 频道 BaseLifecycle onPageShow 时自动派发
     */
    void onListShow(RecyclerView list, String channel);

    /**
     * 频道 BaseLifecycle onPageHide 时自动派发
     */
    void onListHide(RecyclerView list, String channel);

    /**
     * 频道 BaseLifecycle onPageDestroy 时自动派发
     */
    void onListDestroy(RecyclerView list, String channel);

    /**
     * 请根据自己项目的 RecyclerView，使用 {@link IListViewLifecycle.Dispatcher} 进行派发
     */
    void onListScrolled(RecyclerView list, String channel, int dx, int dy);

    /**
     * 请根据自己项目的 RecyclerView，使用 {@link IListViewLifecycle.Dispatcher} 进行派发
     */
    void onListScrollStateChanged(RecyclerView list, String channel, int newState);

    /**
     * 请根据自己项目的 RecyclerView，使用 {@link IListViewLifecycle.Dispatcher} 进行派发
     */
    void onListScrollStateIdle(RecyclerView list, String channel);

    class Holder {
        public RecyclerView recyclerView;
        public String channel;
        public RecyclerView.ViewHolder viewHolder;
    }

    class Dispatcher {

        @Nullable
        private static IListViewLifecycle getLifecycle(RecyclerView list) {
            if (list != null) {
                RecyclerView.Adapter adapter = list.getAdapter();
                if (adapter instanceof BaseWrapperAdapter) {
                    adapter = ((BaseWrapperAdapter) adapter).getInnerAdapter();
                }
                if (adapter instanceof IListViewLifecycle) {
                    return (IListViewLifecycle) adapter;
                }
            }
            return null;
        }

        @Nullable
        private static IListViewLifecycle getLifecycle(RecyclerView list, int index) {
            if (list == null) return null;
            View child = list.getChildAt(index);
            if (child == null) return null;

            RecyclerView.ViewHolder holder = list.getChildViewHolder(child);
            if (holder instanceof IListViewLifecycle) {
                return (IListViewLifecycle) holder;
            }
            if (child instanceof IListViewLifecycle) {
                return (IListViewLifecycle) child;
            }
            if (child.getTag() instanceof IListViewLifecycle) {
                return (IListViewLifecycle) child.getTag();
            }
            return null;
        }

        public static void dispatchOnListRefresh(RecyclerView list, String channel) {
            if (list != null) {
                IListViewLifecycle lifecycle = getLifecycle(list);
                if (lifecycle != null) {
                    lifecycle.onListRefresh(list, channel);
                }
                for (int i = 0; i < list.getChildCount(); i++) {
                    lifecycle = getLifecycle(list, i);
                    if (lifecycle != null) {
                        lifecycle.onListRefresh(list, channel);
                    }
                }
            }
        }

        public static void dispatchOnListShow(RecyclerView list, String channel) {
            if (list != null) {
                IListViewLifecycle lifecycle = getLifecycle(list);
                if (lifecycle != null) {
                    lifecycle.onListShow(list, channel);
                }
                for (int i = 0; i < list.getChildCount(); i++) {
                    lifecycle = getLifecycle(list, i);
                    if (lifecycle != null) {
                        lifecycle.onListShow(list, channel);
                    }
                }
            }
        }

        public static void dispatchOnListHide(RecyclerView list, String channel) {
            if (list != null) {
                IListViewLifecycle lifecycle = getLifecycle(list);
                if (lifecycle != null) {
                    lifecycle.onListHide(list, channel);
                }
                for (int i = 0; i < list.getChildCount(); i++) {
                    lifecycle = getLifecycle(list, i);
                    if (lifecycle != null) {
                        lifecycle.onListHide(list, channel);
                    }
                }
            }
        }

        public static void dispatchOnListDestroy(RecyclerView list, String channel) {
            if (list != null) {
                IListViewLifecycle lifecycle = getLifecycle(list);
                if (lifecycle != null) {
                    lifecycle.onListDestroy(list, channel);
                }
                for (int i = 0; i < list.getChildCount(); i++) {
                    lifecycle = getLifecycle(list, i);
                    if (lifecycle != null) {
                        lifecycle.onListDestroy(list, channel);
                    }
                }
            }
        }

        public static void dispatchOnListScrolled(RecyclerView list, String channel, int dx, int dy) {
            if (list != null) {
                IListViewLifecycle lifecycle = getLifecycle(list);
                if (lifecycle != null) {
                    lifecycle.onListScrolled(list, channel, dx, dy);
                }
                for (int i = 0; i < list.getChildCount(); i++) {
                    lifecycle = getLifecycle(list, i);
                    if (lifecycle != null) {
                        lifecycle.onListScrolled(list, channel, dx, dy);
                    }
                }
            }
        }

        public static void dispatchOnListScrollStateChanged(RecyclerView list, String channel, int newState) {
            if (list != null) {
                IListViewLifecycle lifecycle = getLifecycle(list);
                if (lifecycle != null) {
                    lifecycle.onListScrollStateChanged(list, channel, newState);
                }
                for (int i = 0; i < list.getChildCount(); i++) {
                    lifecycle = getLifecycle(list, i);
                    if (lifecycle != null) {
                        lifecycle.onListScrollStateChanged(list, channel, newState);
                    }
                }
            }
        }

        public static void dispatchOnListScrollStateIdle(RecyclerView list, String channel) {
            if (list != null) {
                IListViewLifecycle lifecycle = getLifecycle(list);
                if (lifecycle != null) {
                    lifecycle.onListScrollStateIdle(list, channel);
                }
                for (int i = 0; i < list.getChildCount(); i++) {
                    lifecycle = getLifecycle(list, i);
                    if (lifecycle != null) {
                        lifecycle.onListScrollStateIdle(list, channel);
                    }
                }
            }
        }

    }

}
