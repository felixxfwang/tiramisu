package org.tiramisu.feeds.holder

import android.view.View
import org.tiramisu.feeds.data.BaseAdapterData

/**
 *
 * @author felixxfwang
 * @date   2019-09-05
 */
class EmptyViewHolder(itemView: View): BaseViewHolder<BaseAdapterData>(itemView) {

    override fun onBindData(data: BaseAdapterData) {
    }

}
