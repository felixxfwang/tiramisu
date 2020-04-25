package org.tiramisu.feeds.holder

import android.util.SparseArray
import android.view.View

fun <T : View> View.findViewOften(viewId: Int): T {
    val holder = tag as? SparseArray<View> ?: SparseArray()
    tag = holder
    var childView: View? = holder.get(viewId)
    if (null == childView) {
        childView = findViewById(viewId)
        holder.put(viewId, childView)
    }
    return childView as T
}