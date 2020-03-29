package org.tiramisu.page.modular

import android.content.Intent
import android.os.Bundle

/**
 *
 * @author felixxfwang
 * @date   2019-12-10
 */
abstract class BasePageModuleManager : IPageModuleManager, IPageModule {

    private val modules = ArrayList<IPageModule>()

    override fun addModule(module: IPageModule) {
        modules.add(module)
        (module as? BasePageModule)?.setModuleManager(this)
    }

    override fun clear() {
        modules.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        modules.forEach { it.onCreate(savedInstanceState) }
    }

    override fun onResume() {
        modules.forEach { it.onResume() }
    }

    override fun onPause() {
        modules.forEach { it.onPause() }
    }

    override fun onPageShow() {
        modules.forEach { it.onPageShow() }
    }

    override fun onPageHide() {
        modules.forEach { it.onPageHide() }
    }

    override fun onDestroy() {
        modules.forEach { it.onDestroy() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        modules.forEach { it.onActivityResult(requestCode, resultCode, data) }
    }
}
