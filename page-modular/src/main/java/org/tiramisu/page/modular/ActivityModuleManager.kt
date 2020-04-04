package org.tiramisu.page.modular

import android.content.Intent
import android.os.Bundle

open class ActivityModuleManager : AbstractModuleManager(), IActivityModule {

    protected val modules by lazy { ArrayList<IActivityModule>() }

    override fun addModule(module: IPageModule) {
        super.addModule(module)
        if (module is IActivityModule) {
            modules.add(module)
        }
    }

    override fun clear() {
        modules.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        modules.forEach { it.onCreate(savedInstanceState) }
    }

    override fun onStart() {
        modules.forEach { it.onStart() }
    }

    override fun onResume() {
        modules.forEach { it.onResume() }
    }

    override fun onPause() {
        modules.forEach { it.onPause() }
    }

    override fun onStop() {
        modules.forEach { it.onStop() }
    }

    override fun onDestroy() {
        modules.forEach { it.onDestroy() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        modules.forEach { it.onActivityResult(requestCode, resultCode, data) }
    }
}