package org.tiramisu.feeds.decorators

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.tiramisu.feeds.adapter.decorator.AdapterLifecycleDecorator
import org.tiramisu.feeds.data.BaseAdapterData

/**
 * 让RecyclerView拥有瀑布流布局
 *
 * @author felixxfwang
 * @date   2019-09-16
 */
class FeedWaterfallDecorator<T : BaseAdapterData>(
    private val spanCount: Int,
    private val orientation: Int,
//    horizontalDividerWidth: Int = HORIZONTAL_DIVIDER_WIDTH,
//    verticalDividerWidth: Int = EPOCH_VERTICAL_DIVIDER,
//    horizontalEdgeWidth: Int = HORIZONTAL_EDGE_WIDTH,
//    verticalEdgeWidth: Int = EPOCH_VERTICAL_DIVIDER,
    dividerColor: Int = Color.WHITE,
    excludeLast: Boolean = false
) : AdapterLifecycleDecorator<T> {

    companion object {
//        val HORIZONTAL_EDGE_WIDTH = dip(12F)
//        val HORIZONTAL_DIVIDER_WIDTH = ViewUtil.dip2px(8F)
//        val EPOCH_VERTICAL_DIVIDER = ViewUtil.dip2px(9F)
    }

//    private val decoration = RecyclerViewItemDecoration(spanCount, horizontalDividerWidth,
//            verticalDividerWidth, horizontalEdgeWidth, verticalEdgeWidth, dividerColor, excludeLast)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        // 设置瀑布流LayoutManager
//        recyclerView.layoutManager = WaterfallLayoutManager(recyclerView, spanCount, orientation)
//         设置瀑布流的Divider
//        recyclerView.addItemDecoration(decoration)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
//        recyclerView.removeItemDecoration(decoration)
    }

    override fun onAdapterScrolledToTop(recyclerView: RecyclerView) {
        // 取消瀑布流顶部空白
        val lm = recyclerView.layoutManager
        if (lm is StaggeredGridLayoutManager) {
            lm.invalidateSpanAssignments()
        }
    }
}
