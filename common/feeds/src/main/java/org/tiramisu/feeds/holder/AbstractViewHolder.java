package org.tiramisu.feeds.holder;

import android.view.View;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;

public class AbstractViewHolder extends RecyclerView.ViewHolder {

    private RecyclerView.Adapter adapter;

    public AbstractViewHolder(View itemView) {
        super(itemView);
    }

    public void bindAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @CallSuper
    public void onRecycled() {
        adapter = null;
    }
}
