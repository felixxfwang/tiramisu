package org.tiramisu.feeds.holder;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * Created by genesisli on 2018/5/14.<br>
 * 列表样式注册
 */
public interface IViewHolderCreator {

    /**
     * 根据 viewType 创建 ViewHolder
     */
    @Nullable
    BaseViewHolder createViewHolder(Context context, ViewGroup parent, int viewType);

}
