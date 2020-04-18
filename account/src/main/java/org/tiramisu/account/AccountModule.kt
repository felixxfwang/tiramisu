package org.tiramisu.account

import android.app.Application
import com.google.auto.service.AutoService
import org.tiramisu.biz.base.AppSettings
import org.tiramisu.http.TiramisuHttp
import org.tiramisu.log.TLog
import org.tiramisu.modular.BaseModule
import org.tiramisu.modular.IModule
import org.tiramisu.page.modular.PageModular

@AutoService(IModule::class)
class AccountModule: BaseModule() {

    companion object {
        private const val TAG = "ProfileModule"
        private const val MODULE_NAME = "profile"
    }

    override fun name(): String = MODULE_NAME

    override fun initialize(application: Application) {
        super.initialize(application)
        TLog.i(TAG, "initialize.")

        TiramisuHttp.initialize(application, AppSettings.isDebugVersion)

        PageModular.initialize(application)
    }

    override fun unload() {
        TLog.i(TAG, "unload.")
    }
}