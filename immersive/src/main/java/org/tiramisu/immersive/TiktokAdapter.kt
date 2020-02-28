package org.tiramisu.immersive

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView

class TiktokAdapter(private val context: Context) : RecyclerView.Adapter<TiktokAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbImage: ImageView = itemView.findViewById(R.id.img_thumb)
        var videoView: VideoView = itemView.findViewById(R.id.video_view)
        var playImage: ImageView = itemView.findViewById(R.id.img_play)
    }

    private val imgs = intArrayOf(
        R.mipmap.img_video_1,
        R.mipmap.img_video_2,
        R.mipmap.img_video_3,
        R.mipmap.img_video_4,
        R.mipmap.img_video_5,
        R.mipmap.img_video_6,
        R.mipmap.img_video_7,
        R.mipmap.img_video_8
    )
    private val videos = intArrayOf(
        R.raw.video_1,
        R.raw.video_2,
        R.raw.video_3,
        R.raw.video_4,
        R.raw.video_5,
        R.raw.video_6,
        R.raw.video_7,
        R.raw.video_8
    )
    private var index = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_immersive_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.thumbImage.setImageResource(imgs[index])
        holder.videoView.setVideoURI(
            Uri.parse("android.resource://" + context.packageName + "/" + videos[index])
        )
        ++index
        index %= 7
    }

    override fun getItemCount(): Int = 88
}