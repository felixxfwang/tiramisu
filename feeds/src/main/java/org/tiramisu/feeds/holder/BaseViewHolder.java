package org.tiramisu.feeds.holder;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import org.tiramisu.feeds.Action1;
import org.tiramisu.feeds.behavior.IListLifecycleBehavior;
import org.tiramisu.feeds.data.IChannelModel;
import org.tiramisu.feeds.data.WriteBackData;
import org.tiramisu.feeds.lifecycle.IListWriteBackHandler;

import java.util.List;

/**
 * Created by genesisli on 2017/9/5.<br>
 * 注意：如果开启了全局复用，一些监听器需要在 bindData 时重新绑定<br>
 */
public abstract class BaseViewHolder<D> extends LifecycleViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemViewClick(v, BaseViewHolder.this);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemViewLongClick(v, BaseViewHolder.this);
                return true;
            }
        });
    }

    private IChannelModel channelPage;

    public void setChannelData(final IChannelModel channelData) {
        this.channelPage = channelData;
        if (this.channelPage != null) {
            dispatch(new Action1<IListLifecycleBehavior>() {
                @Override
                public void call(IListLifecycleBehavior behavior) {
                    behavior.bindChannelModel(channelData);
                }
            });
        }
    }

    @Override
    public void onRecycled() {
        super.onRecycled();
        this.channelPage = null;
    }

    private D mData;

    public D getData() {
        return mData;
    }

    @SuppressWarnings("all")
    public void checkAndBindData(D data, int position, List<Object> payloads) {
        try {
            mData = data;
            if (payloads == null) {
                onBindData((D) data);
            } else {
                onBindData((D) data, payloads);
            }
        } catch (Exception e) {
            onBindDataFailed(e);
        }
    }

    public abstract void onBindData(D data);

    /**
     * 提供默认实现
     */
    public void onBindData(D data, List<Object> payloads){
        onBindData(data);
    }

    protected void onBindDataFailed(Exception e) { }

    public Context getContext() {
        return itemView.getContext();
    }

    /**
     * 是否是预创建的
     */
    private boolean mIsPreCreated = false;

    public boolean isPreCreated() {
        return mIsPreCreated;
    }

    public void setIsPreCreated(boolean isPreCreated) {
        mIsPreCreated = isPreCreated;
    }

    public static void dispatchListWriteBack(RecyclerView list, WriteBackData data) {
        if (list != null && data != null) {
            for (int i = 0; i < list.getChildCount(); i++) {
                RecyclerView.ViewHolder holder = list.getChildViewHolder(list.getChildAt(i));
                if (holder instanceof IListWriteBackHandler) {
                    ((IListWriteBackHandler) holder).onListWriteBack(data);
                }
            }
        }
    }
}
