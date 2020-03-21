package org.tiramisu.immersive

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_tiktok.*
import org.tiramisu.base.BaseActivity
import org.tiramisu.biz.base.RT
import org.tiramisu.feeds.plugins.FeedPagingPlugin
import org.tiramisu.feeds.repository.FeedReqParameter
import org.tiramisu.feeds.repository.LoadInitialCallback
import org.tiramisu.feeds.repository.LoadMoreCallback
import org.tiramisu.immersive.data.Video
import org.tiramisu.log.TLog
import org.tiramisu.immersive.repository.VideoFeedsRepository
import org.tiramisu.player.TMVideoView

@Route(path = RT.Immersive.TIKTOK)
class TiktokActivity : BaseActivity(),
    LoadInitialCallback<FeedReqParameter, List<Video>>,
    LoadMoreCallback<FeedReqParameter, List<Video>> {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val repository = VideoFeedsRepository()
    private val req = FeedReqParameter()

    private val adapter by lazy {
        TiktokAdapter().apply {
            addAdapterPlugin(FeedPagingPlugin(repository, req, 10, this@TiktokActivity))
        }
    }
    private val layoutManager by lazy {
        TiktokLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiktok)
        initView()

        repository.loadInitial(req, this)
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

    override fun onLoadDataSuccess(param: FeedReqParameter, data: List<Video>) {
        adapter.setAdapterData(data)
    }

    override fun onLoadDataFailed(param: FeedReqParameter, errorCode: Int, errorMsg: String?) {

    }

    override fun onLoadMoreSuccess(param: FeedReqParameter, data: List<Video>, isLastPage: Boolean) {
        adapter.addAdapterData(data)
    }

    override fun onLoadMoreFailed(param: FeedReqParameter, errorCode: Int, errorMsg: String?) {

    }
}