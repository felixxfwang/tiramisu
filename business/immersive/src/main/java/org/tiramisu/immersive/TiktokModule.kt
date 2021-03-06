package org.tiramisu.immersive

import android.app.Application
import com.google.auto.service.AutoService
import org.tiramisu.biz.base.AppSettings
import org.tiramisu.feeds.holder.ViewHolderRegister
import org.tiramisu.http.TiramisuHttp
import org.tiramisu.log.TLog
import org.tiramisu.modular.BaseModule
import org.tiramisu.modular.IModule
import org.tiramisu.page.modular.PageModular

@AutoService(IModule::class)
class TiktokModule: BaseModule() {

    companion object {
        private const val TAG = "ImmersiveModule"
        private const val MODULE_NAME = "immersive"
    }

    override fun name(): String = MODULE_NAME

    override fun initialize(application: Application) {
        super.initialize(application)
        TLog.i(TAG, "initialize.")

        TiramisuHttp.initialize(application, AppSettings.isDebugVersion)

        ViewHolderRegister.registerExtraCreator(TiktokViewHolderCreator())

        PageModular.initialize(application)
    }

    override fun unload() {
        TLog.i(TAG, "unload.")
    }
}