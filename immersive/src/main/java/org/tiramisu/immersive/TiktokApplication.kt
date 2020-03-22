package org.tiramisu.immersive

import org.tiramisu.base.BaseApplication
import org.tiramisu.modular.ModularManager

class TiktokApplication : BaseApplication() {
    private val modular = ModularManager()

    override fun onCreate() {
        super.onCreate()

        modular.initialize(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        modular.unload()
    }
}