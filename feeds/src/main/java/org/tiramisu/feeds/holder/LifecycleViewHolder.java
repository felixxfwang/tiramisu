package org.tiramisu.feeds.holder;

import android.view.View;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.tiramisu.feeds.Action1;
import org.tiramisu.feeds.behavior.IBusinessBehavior;
import org.tiramisu.feeds.behavior.IListLifecycleBehavior;
import org.tiramisu.feeds.data.WriteBackData;
import org.tiramisu.feeds.lifecycle.IListViewHolderLifecycle;
import org.tiramisu.feeds.lifecycle.IListViewLifecycle;
import org.tiramisu.feeds.lifecycle.IListWriteBackHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewHolder 基类，专职负责处理生命周期相关逻辑 <br>
 * <b>使用注意：</b>
 * <ol>
 * <li>这个类只维护生命周期，不需添加其他业务逻辑</li>
 * <li>对于依赖于生命周期的业务逻辑，本类提供了注册 IBehavior 的机制，IBehavior 会收到所有生命周期回调，将业务逻辑实现为一个 IBehavior 更利于解耦和复用</li>
 * </ol>
 */
public class LifecycleViewHolder extends AbstractViewHolder
        implements IListViewLifecycle, IListViewHolderLifecycle, IBusinessBehavior, IListWriteBackHandler {

    private final List<IListLifecycleBehavior> mLifecycleBehaviors = new ArrayList<>();
    private final List<IListLifecycleBehavior> mLifecyclePostProcessBehaviors = new ArrayList<>();

    LifecycleViewHolder(View itemView) {
        super(itemView);
        onRegisterLifecycleBehavior(mLifecycleBehaviors);
        onRegisterLifecyclePostProcessBehavior(mLifecyclePostProcessBehaviors);
    }

    //我觉得还是有必要添加一个这个接口的，但是得谨慎使用它
    public void registerLifecyclePostProcessBehavior(IListLifecycleBehavior behavior) {
        if (!mLifecyclePostProcessBehaviors.contains(behavior)) {
            mLifecyclePostProcessBehaviors.add(behavior);
        }
    }

    protected void dispatch(Action1<IListLifecycleBehavior> callback) {
        if (callback == null) return;
        for (IListLifecycleBehavior behavior : mLifecycleBehaviors) {
            if (behavior != null) {
                callback.call(behavior);
            }
        }
        // 派发流程中有一些需要考虑时序问题的，可以放在后置处理
        // 一些自定义的新生命周期（如：onFullIdleInScreen）普遍放这
        for (IListLifecycleBehavior behavior : mLifecyclePostProcessBehaviors) {
            if (behavior != null) {
                callback.call(behavior);
            }
        }
    }

    @CallSuper
    @Override
    public void onExposure(final RecyclerView list, final RecyclerView.ViewHolder viewHolder) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onExposure(list, viewHolder);
            }
        });
    }

    /**
     * 给viewHolder中的view设置点击事件，点击事件统一由behavior处理，所以一个view只绑定一次点击事件
     */
    protected void bindViewClick(View view) {
        bindViewClick(view, false);
    }

    /**
     *
     * @param force 是否强制重新设置点击事件
     */
    protected void bindViewClick(final View view, boolean force) {
        if (force || !view.hasOnClickListeners()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewClick(view, LifecycleViewHolder.this);
                }
            });
        }
    }

    protected void bindViewLongClick(final View view) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onViewLongClick(view, LifecycleViewHolder.this);
                return true;
            }
        });
    }

    @Override
    public void onItemViewClick(final View view, final RecyclerView.ViewHolder viewHolder) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onItemViewClick(view, viewHolder);
            }
        });
    }

    @Override
    public void onItemViewLongClick(final View view, final RecyclerView.ViewHolder viewHolder) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onItemViewLongClick(view, viewHolder);
            }
        });
    }

    @CallSuper
    @Override
    public void onViewClick(final View view, final RecyclerView.ViewHolder viewHolder) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onViewClick(view, viewHolder);
            }
        });
    }

    @Override
    public void onViewLongClick(final View view, final RecyclerView.ViewHolder viewHolder) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onViewLongClick(view, viewHolder);
            }
        });
    }

    @Override
    public void onListRefresh(final RecyclerView list, final String channel) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onListRefresh(list, channel);
            }
        });
    }

    @CallSuper
    @Override
    public void onListShow(final RecyclerView list, final String channel) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onListShow(list, channel);
            }
        });
    }

    @CallSuper
    @Override
    public void onListHide(final RecyclerView list, final String channel) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onListHide(list, channel);
            }
        });
    }

    @Override
    public void onListDestroy(final RecyclerView list, final String channel) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onListDestroy(list, channel);
            }
        });
    }

    @CallSuper
    @Override
    public void onListScrolled(final RecyclerView list, final String channel, final int dx, final int dy) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onListScrolled(list, channel, dx, dy);
            }
        });
    }

    @CallSuper
    @Override
    public void onListScrollStateChanged(final RecyclerView list, final String channel, final int newState) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onListScrollStateChanged(list, channel, newState);
            }
        });
    }

    @CallSuper
    @Override
    public void onListScrollStateIdle(final RecyclerView list, final String channel) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onListScrollStateIdle(list, channel);
            }
        });
    }

    @CallSuper
    @Override
    public void onAttachedToWindow(final RecyclerView.ViewHolder viewHolder) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onAttachedToWindow(viewHolder);
            }
        });
    }

    @CallSuper
    @Override
    public void onDetachedFromWindow(final RecyclerView.ViewHolder viewHolder) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onDetachedFromWindow(viewHolder);
            }
        });
    }

    /**
     * 列表滑动中，发生的 ViewHolder detach（这种可以判断是滑动导致的detach，不是原地notify导致的detach-attach）
     */
    @Override
    public void onScrollDetachedFromWindow(final RecyclerView.ViewHolder viewHolder) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onScrollDetachedFromWindow(viewHolder);
            }
        });
    }

    @CallSuper
    @Override
    public void onFullIdleInScreen(final RecyclerView.ViewHolder viewHolder, final String channel, final int top, final int bottom, final int containerTop, final int containerBottom) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onFullIdleInScreen(viewHolder, channel, top, bottom, containerTop, containerBottom);
            }
        });
    }

    @CallSuper
    @Override
    public void onBottomIdleInScreen(final RecyclerView.ViewHolder viewHolder, final String channel, final int top, final int bottom, final int containerTop, final int containerBottom) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onBottomIdleInScreen(viewHolder, channel, top, bottom, containerTop, containerBottom);
            }
        });
    }

    @Override
    public void onDismiss(final RecyclerView.ViewHolder viewHolder) {
        dispatch(new Action1<IListLifecycleBehavior>() {
            @Override
            public void call(IListLifecycleBehavior behavior) {
                behavior.onDismiss(viewHolder);
            }
        });
    }

    @Override
    public void onListWriteBack(@NotNull WriteBackData data) {
    }

    public final void notifyAttachedToWindow(RecyclerView recyclerView) {
        bindAdapter(recyclerView.getAdapter());
        onAttachedToWindow(this);
    }

    public final void notifyDetachedFromWindow(RecyclerView recyclerView) {
        bindAdapter(null);
        onDetachedFromWindow(this);

        if (recyclerView != null) {
            int state = recyclerView.getScrollState();
            if (state == RecyclerView.SCROLL_STATE_DRAGGING || state == RecyclerView.SCROLL_STATE_SETTLING) {
                onScrollDetachedFromWindow(this); // 拖拽或fling
            }
        }
    }

    /**
     * @param behaviors 每一种业务逻辑，都抽象出一个 behavior，注册在这里；业务彼此隔离，只依赖生命周期，方便解耦、复用
     */
    protected void onRegisterLifecycleBehavior(List<IListLifecycleBehavior> behaviors) {
    }

    /**
     * 注册 后处理 的 behavior，当 {@link #onRegisterLifecycleBehavior} 都被回调完之后，才会回调这里的 behavior <br>
     * 普遍用于扩展一些生命周期逻辑
     */
    protected void onRegisterLifecyclePostProcessBehavior(List<IListLifecycleBehavior> behaviors) {
    }

}
