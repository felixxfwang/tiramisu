package org.tiramisu.immersive

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_tiktok.*
import org.tiramisu.base.BaseActivity
import org.tiramisu.biz.base.RT
import org.tiramisu.immersive.repository.VideoQueryCallback
import org.tiramisu.immersive.data.VideoQueryParam
import org.tiramisu.log.TLog
import org.tiramisu.immersive.data.VideoQueryResult
import org.tiramisu.player.TMVideoView

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

    override fun onPause() {
        super.onPause()
        TMVideoView.pause()
    }

    override fun onBackPressed() {
        if (!TMVideoView.back()) {
            super.onBackPressed()
        }
    }

    private fun initView() {
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter

        layoutManager.setOnViewPagerListener(object : OnSnapListener {
            override fun onPageUnselected(isNext: Boolean, position: Int) {
                TLog.i(TAG, "onPageUnselected:$position isNext:$isNext")
            }

            override fun onPageSelected(position: Int, bottom: Boolean) {
                TLog.i(TAG, "onPageSelected:$position isBottom:$bottom")
            }
        })
    }


    private val param = VideoQueryParam(0)

    private fun queryVideos() {
        VideoService.getVideos(param, object :
            VideoQueryCallback {
            override fun onSuccess(param: VideoQueryParam, data: VideoQueryResult) {
                if (param.page == 0) {
                    adapter.setData(data.videos)
                } else {
                    adapter.addData(data.videos)
                }
            }

            override fun onError(param: VideoQueryParam, errorCode: Int, errorMessage: String?) {
                TLog.e(TAG, "queryVideos failed: errorCode=$errorCode, errorMsg: $errorMessage")
            }
        })
        ++param.page
    }
}