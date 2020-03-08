package org.tiramisu.modular

import android.app.Application
import java.util.*

class ModularManager: IModule {

    companion object {
        private const val TAG = "ModuleManager"
    }

    private val loader = ServiceLoader.load(IModule::class.java)

    fun modules(): Iterator<IModule> {
        return loader.iterator()
    }

    override fun name(): String = TAG

    override fun initialize(application: Application) {
        modules().forEach { it.initialize(application) }
    }

    override fun unload() {
        modules().forEach { it.unload() }
    }
}