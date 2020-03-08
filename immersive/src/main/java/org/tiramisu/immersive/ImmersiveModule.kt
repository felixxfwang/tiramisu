package org.tiramisu.immersive

import android.app.Application
import com.google.auto.service.AutoService
import org.tiramisu.log.TLog
import org.tiramisu.modular.IModule

@AutoService(IModule::class)
class ImmersiveModule: IModule {

    companion object {
        private const val TAG = "ImmersiveModule"
        private const val MODULE_NAME = "immersive"
    }

    override fun name(): String = MODULE_NAME

    override fun initialize(application: Application) {
        TLog.i(TAG, "initialize.")
    }

    override fun unload() {
        TLog.i(TAG, "unload.")
    }
}