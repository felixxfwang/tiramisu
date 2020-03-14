package org.tiramisu.immersive

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import org.tiramisu.log.TLog
import org.tiramisu.network.service.Video

class TiktokAdapter(private val context: Context) : RecyclerView.Adapter<TiktokAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "TiktokAdapter"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbImage: ImageView = itemView.findViewById(R.id.img_thumb)
        var videoView: StandardGSYVideoPlayer = itemView.findViewById(R.id.video_view)
        var playImage: ImageView = itemView.findViewById(R.id.img_play)
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
        holder.videoView.setUp(video.video_url, true, video.video_title + "position: " + position)
        holder.playImage.setOnClickListener {
            if (holder.videoView.isInPlayingState) {
                holder.playImage.animate().alpha(0.7f).start()
                holder.videoView.onVideoPause()
            } else {
                holder.playImage.animate().alpha(0f).start()
                holder.videoView.onVideoResume()
            }
        }
    }

    override fun getItemCount(): Int = dataList.size
}