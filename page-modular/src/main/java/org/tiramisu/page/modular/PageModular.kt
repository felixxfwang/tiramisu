package org.tiramisu.page.modular

import android.app.Application

object PageModular {

    fun initialize(application: Application) {
        application.registerActivityLifecycleCallbacks(ModularActivityLifecycleCallbacks)
    }

}