package org.tiramisu.account

import org.tiramisu.base.BaseApplication
import org.tiramisu.modular.ModularManager

class AccountApplication : BaseApplication() {

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