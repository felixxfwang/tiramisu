package org.tiramisu.feeds.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by genesisli on 2017/9/6.<br>
 * 根据 viewType（约定使用布局id） 创建对应的 {@link BaseViewHolder}
 */
public abstract class BaseViewHolderCreator {

    public static View inflate(@NonNull ViewGroup parent, int viewType) {
        return inflate(parent.getContext(), parent, viewType);
    }

    public static View inflate(@NonNull Context context, @Nullable ViewGroup parent, int viewType) {
        return LayoutInflater.from(context).inflate(viewType, parent, false);
    }

}
