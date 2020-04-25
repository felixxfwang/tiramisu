package org.tiramisu.immersive.tiktok

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.tiramisu.feeds.behavior.IListLifecycleBehavior
import org.tiramisu.feeds.holder.BaseFeedViewHolder
import org.tiramisu.image.options
import org.tiramisu.image.with
import org.tiramisu.immersive.R
import org.tiramisu.immersive.data.PostData
import org.tiramisu.player.TMVideoView

class TiktokViewHolder(itemView: View) : BaseFeedViewHolder<PostData>(itemView) {

    private val videoView = findView<TMVideoView>(R.id.video_view)
    private val videoCover = findView<ImageView>(R.id.img_thumb)

    override fun onRegisterLifecycleBehavior(behaviors: MutableList<IListLifecycleBehavior>?) {
        behaviors?.add(TiktokClickBehavior())
    }

    override fun onBindData(data: PostData) {
        val post = data.post
        val video = data.post.video
        val user = data.post.user
        videoCover.with(context).load(video.cover_url)
        videoView.setUp(video.video_url)
        findView<TextView>(R.id.video_title).text = video.video_title
        findView<TextView>(R.id.post_author).text = "@${user.username}"
        findView<TextView>(R.id.text_like_count).text = post.like_count.toString()
        findView<TextView>(R.id.text_comment_count).text = post.comment_count.toString()
        findView<TextView>(R.id.text_share_count).text = post.share_count.toString()
        val options = options()
            .placeholder(R.mipmap.header_icon_4)
            .error(R.mipmap.header_icon_4)
            .asCircle()
        findView<ImageView>(R.id.avatar).with(context).options(options).load(user.avatar)

        bindViewClick(R.id.avatar)
        bindViewClick(R.id.text_like_count)
        bindViewClick(R.id.text_comment_count)
        bindViewClick(R.id.text_share_count)
    }

    fun play() {
        videoView.start()
        videoCover.visibility = View.INVISIBLE
    }

    fun pause() {
        videoView.pause()
//        videoCover.visibility = View.INVISIBLE
    }

}