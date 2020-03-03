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

    private var index = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_immersive_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TLog.i(TAG, "onBindViewHolder: position: $position")
        val video = dataList[index]
        holder.thumbImage.setImageURI(Uri.parse(video.cover_url))
        holder.videoView.setUp(video.video_url, true, video.video_title)
        index = ++index % itemCount
    }

    override fun getItemCount(): Int = dataList.size
}