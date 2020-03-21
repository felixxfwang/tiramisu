package org.tiramisu.feeds.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * 创建应用内公用的列表样式
 */
public class ViewHolderCreator {

    @NonNull
    public static BaseViewHolder create(ViewGroup parent, int viewType) {
        return create(parent.getContext(), parent, viewType);
    }

    public static EmptyViewHolder createEmptyViewHolder(Context context) {
        View emptyView = new View(context);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        return new EmptyViewHolder(emptyView);
    }

    /**
     * 创建列表
     */
    @NonNull
    public static BaseViewHolder create(Context context, ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = ViewHolderRegister.createViewHolder(context, parent, viewType);
        if (viewHolder != null) {
            return viewHolder;
        }
        return createEmptyViewHolder(context);
    }

}
