package org.tiramisu.immersive.tiktok

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tiramisu.feeds.behavior.BaseBehavior
import org.tiramisu.immersive.R
import org.tiramisu.immersive.data.PostData

class TiktokClickBehavior : BaseBehavior<PostData>() {

    override fun onViewClick(
        view: View,
        data: PostData,
        holder: RecyclerView.ViewHolder,
        activity: Context
    ) {
        when (view.id) {
            R.id.avatar -> {}
            R.id.btn_follow -> {

            }
            R.id.text_like_count -> {
                findView<TextView>(R.id.text_like_count, holder)?.let {
                    it.text = data.post.like_count.plus(1).toString()
                    it.isSelected = true
                }
            }
            R.id.text_comment_count -> {}
            R.id.text_share_count -> {}
        }
    }

}