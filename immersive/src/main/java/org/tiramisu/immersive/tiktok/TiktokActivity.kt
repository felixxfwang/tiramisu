package org.tiramisu.immersive.tiktok

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_tiktok.*
import org.tiramisu.base.BaseActivity
import org.tiramisu.biz.base.RT
import org.tiramisu.feeds.plugins.FeedPagingDecorator
import org.tiramisu.feeds.repository.FeedReqParameter
import org.tiramisu.feeds.repository.LoadInitialCallback
import org.tiramisu.feeds.repository.LoadMoreCallback
import org.tiramisu.immersive.R
import org.tiramisu.immersive.data.VideoData
import org.tiramisu.log.TLog
import org.tiramisu.immersive.repository.VideoFeedsRepository
import org.tiramisu.player.TMVideoView

@Route(path = RT.Immersive.TIKTOK)
class TiktokActivity : BaseActivity(), OnSnapListener,
    LoadInitialCallback<FeedReqParameter, List<VideoData>>,
    LoadMoreCallback<FeedReqParameter, List<VideoData>> {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val repository = VideoFeedsRepository()
    private val req = FeedReqParameter()

    private val adapter by lazy {
        TiktokAdapter().apply {
            addDecorator(FeedPagingDecorator(repository, req, 5, this@TiktokActivity))
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

        layoutManager.setOnViewPagerListener(this)
    }

    override fun onPageUnselected(isNext: Boolean, position: Int) {
        TLog.i(TAG, "onPageUnselected:$position isNext:$isNext")
    }

    override fun onPageSelected(position: Int, bottom: Boolean) {
        TLog.i(TAG, "onPageSelected:$position isBottom:$bottom")
    }

    override fun onLoadDataSuccess(param: FeedReqParameter, data: List<VideoData>) {
        adapter.setAdapterData(data)
    }

    override fun onLoadMoreSuccess(param: FeedReqParameter, data: List<VideoData>, isLastPage: Boolean) {
        adapter.addAdapterData(data)
    }
}