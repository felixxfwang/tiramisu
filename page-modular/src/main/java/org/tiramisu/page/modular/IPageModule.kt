package org.tiramisu.page.modular

import android.content.Intent
import android.os.Bundle

/**
 *
 * @author felixxfwang
 * @date   2019-10-24
 */
interface IPageModule {

    /**
     * 对应fragment的onCreate
     */
    fun onCreate(savedInstanceState: Bundle?) {}

    /**
     * 对应Fragment的onResume
     */
    fun onResume() {}

    /**
     * 对应Fragment的onPause
     */
    fun onPause() {}

    /**
     * fragment可见
     */
    fun onPageShow() {}

    /**
     * fragment不可见
     */
    fun onPageHide() {}

    /**
     * 对应fragment的onDestroy
     */
    fun onDestroy() {}

    /**
     * 页面回调
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
}
