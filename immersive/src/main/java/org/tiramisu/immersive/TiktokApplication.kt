package org.tiramisu.immersive

import org.tiramisu.base.BaseApplication
import org.tiramisu.modular.IModule
import java.util.*

class TiktokApplication : BaseApplication() {

    private val modules = ServiceLoader.load(IModule::class.java)

    override fun onCreate() {
        super.onCreate()

        modules.forEach { it.initialize(this) }
    }

    override fun onTerminate() {
        super.onTerminate()
        modules.forEach { it.unload() }
    }
}