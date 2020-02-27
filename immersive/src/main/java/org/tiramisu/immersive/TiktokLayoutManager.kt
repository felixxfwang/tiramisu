package org.tiramisu.immersive

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

/**
 */
class TiktokLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean)
    : LinearLayoutManager(context, orientation, reverseLayout), RecyclerView.OnChildAttachStateChangeListener {

    private var snapHelper = PagerSnapHelper()
    private var listener: OnViewPagerListener? = null
    private var diffY = 0

    fun setOnViewPagerListener(listener: OnViewPagerListener) {
        this.listener = listener
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        view.addOnChildAttachStateChangeListener(this)
        snapHelper.attachToRecyclerView(view)
    }

    override fun onChildViewDetachedFromWindow(view: View) {
        val position = getPosition(view)
        if (0 < diffY) {
            listener?.onPageRelease(true, position)
        } else {
            listener?.onPageRelease(false, position)
        }
    }

    override fun onChildViewAttachedToWindow(view: View) {
        val position = getPosition(view)
        if (0 == position) {
            listener?.onPageSelected(position, false)
        }
    }

    override fun onScrollStateChanged(state: Int) {
        if (RecyclerView.SCROLL_STATE_IDLE == state) {
            snapHelper.findSnapView(this)?.let { view ->
                val position = getPosition(view)
                listener?.onPageSelected(position, position == itemCount - 1)
            }
        }
        super.onScrollStateChanged(state)
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        diffY = dy
        return super.scrollVerticallyBy(dy, recycler, state)
    }

}