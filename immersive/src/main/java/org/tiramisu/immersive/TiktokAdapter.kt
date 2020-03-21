package org.tiramisu.immersive

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import org.tiramisu.feeds.adapter.BaseAdapter
import org.tiramisu.feeds.holder.BaseViewHolder
import org.tiramisu.immersive.data.Video
import org.tiramisu.log.TLog
import org.tiramisu.player.TMVideoView

class TiktokAdapter : BaseAdapter<Video, TiktokAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "TiktokAdapter"
    }

    class ViewHolder(itemView: View) : BaseViewHolder<Video>(itemView) {
        var thumbImage: ImageView = itemView.findViewById(R.id.img_thumb)
        var videoView: TMVideoView = itemView.findViewById(R.id.video_view)
        var playImage: ImageView = itemView.findViewById(R.id.img_play)

        override fun onBindData(data: Video) {

        }
    }

    private val dataList = ArrayList<Video>()

    fun setData(data: List<Video>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: List<Video>) {
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_immersive_video, parent, false)
        return ViewHolder(view).apply {
            TLog.i(TAG, "onCreateViewHolder: viewType: $viewType, videoView: ${videoView.hashCode()}")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = dataList[position]
        TLog.i(TAG, "onBindViewHolder: position: $position, videoView: ${holder.videoView.hashCode()}")
        holder.thumbImage.setImageURI(Uri.parse(video.cover_url))
        holder.videoView.setUp(video.video_url)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        holder.videoView.start()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.videoView.pause()
        // 这里千万不能做动画，否则ItemView会处于Transient State，从而导致holder无法被复用
//        holder.thumbImage.animate().alpha(1f).start()
//        holder.playImage.animate().alpha(0f).start()
    }

    override fun getItemCount(): Int = dataList.size

}