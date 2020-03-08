package org.tiramisu

import android.app.Application
import org.tiramisu.modular.ModuleManager

class BaseApplication: Application() {

    private val moduleManager = ModuleManager()

    override fun onCreate() {
        super.onCreate()

        moduleManager.initialize(this)
    }

    override fun onTerminate() {
        super.onTerminate()

        moduleManager.unload()
    }
}