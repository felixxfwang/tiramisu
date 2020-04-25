package org.tiramisu.feeds.holder;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 注册列表样式 creator
 */
public class ViewHolderRegister {

    private static final Collection<IViewHolderCreator> sExtraCreatorList = new ArrayList<IViewHolderCreator>();

    /**
     * 注意注册顺序，会影响 creator 的判断顺序
     */
    public static void registerExtraCreator(@NonNull IViewHolderCreator creator) {
        sExtraCreatorList.add(creator);
    }

    public static void unRegisterExtraCreator(@NonNull IViewHolderCreator creator) {
        sExtraCreatorList.remove(creator);
    }

    public static void clearAllCreator() {
        sExtraCreatorList.clear();
    }

    @Nullable
    public static BaseViewHolder createViewHolder(Context context, ViewGroup parent, int viewType) {
        for (IViewHolderCreator extraCreator : sExtraCreatorList) {
            if (extraCreator != null) {
                BaseViewHolder viewHolder = extraCreator.createViewHolder(context, parent, viewType);
                if (viewHolder != null) {
                    return viewHolder;
                }
            }
        }
        return null;
    }

}
