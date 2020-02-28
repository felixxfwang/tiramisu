package org.tiramisu.immersive

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.tiramisu.base.BaseActivity

class TiktokActivity : BaseActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val adapter by lazy { TiktokAdapter(this) }
    private val layoutManager by lazy {
        TiktokLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter

        layoutManager.setOnViewPagerListener(object :
            OnSnapListener {
            override fun onPageRelease(isNext: Boolean, position: Int) {
                Log.e(TAG, "释放位置:$position 下一页:$isNext")
                val index = if (isNext) 0 else 1
                releaseVideo(index)
            }

            override fun onPageSelected(position: Int, bottom: Boolean) {
                Log.e(TAG, "选择位置:$position 下一页:$bottom")
                playVideo(0)
            }
        })
    }

    private fun releaseVideo(index: Int) {
        val itemView = recycler.getChildAt(index)
        val videoView = itemView.findViewById<VideoView>(R.id.video_view)
        val imgThumb = itemView.findViewById<ImageView>(R.id.img_thumb)
        val imgPlay = itemView.findViewById<ImageView>(R.id.img_play)
        videoView.stopPlayback()
        imgThumb.animate().alpha(1f).start()
        imgPlay.animate().alpha(0f).start()
    }

    private fun playVideo(position: Int) {
        val itemView = recycler.getChildAt(position)
        val videoView: FullWindowVideoView = itemView.findViewById(R.id.video_view)
        val imgPlay = itemView.findViewById<ImageView>(R.id.img_play)
        val imgThumb = itemView.findViewById<ImageView>(R.id.img_thumb)
        videoView.setOnInfoListener { mp, what, extra ->
            mp.isLooping = true
            imgThumb.animate().alpha(0f).setDuration(200).start()
            false
        }
        videoView.start()
        imgPlay.setOnClickListener {
            if (videoView.isPlaying) {
                imgPlay.animate().alpha(0.7f).start()
                videoView.pause()
            } else {
                imgPlay.animate().alpha(0f).start()
                videoView.start()
            }
        }
    }
}