package org.tiramisu.feeds.adapter

import androidx.recyclerview.widget.RecyclerView
import org.tiramisu.feeds.data.BaseAdapterData

/**
 *
 * @author felixxfwang
 * @date   2019-10-11
 */
abstract class AbsAdapter<T : BaseAdapterData, VH : RecyclerView.ViewHolder>
    : RecyclerView.Adapter<VH>(), IAdapter<T> {

    final override fun onBindViewHolder(holder: VH, position: Int) {}
}
