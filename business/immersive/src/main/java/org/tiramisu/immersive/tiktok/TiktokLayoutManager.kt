package org.tiramisu.immersive.tiktok

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.tiramisu.log.TLog

/**
 */
class TiktokLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean)
    : LinearLayoutManager(context, orientation, reverseLayout), RecyclerView.OnChildAttachStateChangeListener {

    companion object {
        private const val TAG = "TiktokLayoutManager"
    }

    private var snapHelper = PagerSnapHelper()
    private var listener: OnSnapListener? = null
    private var diffY = 0

    private var selectedPosition = -1

    fun setOnViewPagerListener(listener: OnSnapListener) {
        this.listener = listener
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        view.addOnChildAttachStateChangeListener(this)
        snapHelper.attachToRecyclerView(view)
    }

    override fun onChildViewDetachedFromWindow(view: View) {
        val position = getPosition(view)
        TLog.i(TAG, "onChildViewDetachedFromWindow: position=$position, diffY=$diffY")
        if (position == selectedPosition) {
            val isNext = diffY > 0
            listener?.onPageUnselected(isNext, position)
        }
    }

    override fun onChildViewAttachedToWindow(view: View) {
        val position = getPosition(view)
        TLog.i(TAG, "onChildViewAttachedToWindow: position=$position")
        if (0 == position) {
            onPageSelected(position, false)
        }
    }

    override fun onScrollStateChanged(state: Int) {
        if (RecyclerView.SCROLL_STATE_IDLE == state) {
            snapHelper.findSnapView(this)?.let { view ->
                val position = getPosition(view)
                onPageSelected(position, position == itemCount - 1)
            }
        }
        super.onScrollStateChanged(state)
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        diffY = dy
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    private fun onPageSelected(position: Int, isBottom: Boolean) {
        if (position != selectedPosition) {
            selectedPosition = position
            listener?.onPageSelected(position, isBottom)
        }
    }

}