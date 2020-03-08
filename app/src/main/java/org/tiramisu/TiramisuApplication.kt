package org.tiramisu

import android.content.Context
import androidx.multidex.MultiDex
import org.tiramisu.base.BaseApplication
import org.tiramisu.modular.ModularManager

class TiramisuApplication: BaseApplication() {

    private val moduleManager = ModularManager()

    override fun onCreate() {
        super.onCreate()

        moduleManager.initialize(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onTerminate() {
        super.onTerminate()

        moduleManager.unload()
    }
}