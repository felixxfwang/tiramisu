package org.tiramisu.immersive

import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.android.synthetic.main.activity_tiktok.*
import org.tiramisu.base.BaseActivity
import org.tiramisu.biz.base.RT
import org.tiramisu.log.TLog
import org.tiramisu.network.service.VideoQueryResult
import org.tiramisu.network.service.VideoService
import org.tiramisu.network.service.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Route(path = RT.Immersive.TIKTOK)
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
        setContentView(R.layout.activity_tiktok)
        initView()

        queryVideos()
    }

    private fun initView() {
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter

        layoutManager.setOnViewPagerListener(object : OnSnapListener {
            override fun onPageRelease(isNext: Boolean, position: Int) {
                TLog.i(TAG, "释放位置:$position 下一页:$isNext")
                val index = if (isNext) 0 else 1
                releaseVideo(index)
            }

            override fun onPageSelected(position: Int, bottom: Boolean) {
                TLog.i(TAG, "选择位置:$position 下一页:$bottom")
                playVideo(0)
            }
        })
    }

    private fun queryVideos() {
        retrofit.create(VideoService::class.java).getVideos().enqueue(object : Callback<VideoQueryResult> {
            override fun onFailure(call: Call<VideoQueryResult>, t: Throwable) {
                TLog.e(TAG, "queryVideos failed", t)
            }

            override fun onResponse(
                call: Call<VideoQueryResult>,
                response: Response<VideoQueryResult>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        adapter.setData(it.videos)
                    }
                }
            }

        })
    }

    private fun releaseVideo(index: Int) {
        val itemView = recycler.getChildAt(index)
        val videoView = itemView.findViewById<StandardGSYVideoPlayer>(R.id.video_view)
        val imgThumb = itemView.findViewById<ImageView>(R.id.img_thumb)
        val imgPlay = itemView.findViewById<ImageView>(R.id.img_play)
        videoView.onVideoPause()
        imgThumb.animate().alpha(1f).start()
        imgPlay.animate().alpha(0f).start()
    }

    private fun playVideo(position: Int) {
        val itemView = recycler.getChildAt(position)
        val videoView = itemView.findViewById<StandardGSYVideoPlayer>(R.id.video_view)
        val imgPlay = itemView.findViewById<ImageView>(R.id.img_play)
        videoView.startPlayLogic()
        imgPlay.setOnClickListener {
            if (videoView.isInPlayingState) {
                imgPlay.animate().alpha(0.7f).start()
                videoView.onVideoPause()
            } else {
                imgPlay.animate().alpha(0f).start()
                videoView.onVideoResume()
            }
        }
    }
}