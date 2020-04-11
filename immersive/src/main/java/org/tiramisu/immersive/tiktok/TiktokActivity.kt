package org.tiramisu.immersive.tiktok

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_tiktok.*
import org.tiramisu.base.BaseActivity
import org.tiramisu.biz.base.RT
import org.tiramisu.immersive.R
import org.tiramisu.log.TLog
import org.tiramisu.player.TMVideoView
import kotlin.properties.Delegates

@Route(path = RT.Immersive.TIKTOK)
class TiktokActivity : BaseActivity(), OnSnapListener {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var viewModel: TiktokViewModel by Delegates.notNull()

    private val layoutManager by lazy {
        TiktokLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiktok)

        viewModel = ViewModelProvider(this).get(TiktokViewModel::class.java)

        initView()

        viewModel.loadInitial()
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
        recycler.adapter = viewModel.adapter

        layoutManager.setOnViewPagerListener(this)
    }

    override fun onPageUnselected(isNext: Boolean, position: Int) {
        TLog.i(TAG, "onPageUnselected:$position isNext:$isNext")
    }

    override fun onPageSelected(position: Int, bottom: Boolean) {
        TLog.i(TAG, "onPageSelected:$position isBottom:$bottom")
    }
}