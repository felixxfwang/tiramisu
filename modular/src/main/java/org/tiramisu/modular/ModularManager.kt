package org.tiramisu.modular

import android.app.Application
import java.util.*

class ModularManager: IModule {

    companion object {
        private const val TAG = "ModuleManager"
    }

    private val moduleMap = HashMap<String, IModule>()

    init {
        ServiceLoader.load(IModule::class.java).forEach {
            moduleMap[it.name()] = it
        }
    }

    fun modules() = moduleMap.values

    override fun name(): String = TAG

    override fun initialize(application: Application) {
        modules().forEach { module ->
            if (!module.isInitialized()) {
                checkDependsInitialize(module, application)
                module.initialize(application)
            }
        }
    }

    private fun checkDependsInitialize(module: IModule, app: Application) {
        val depends = module.dependsOn()
        if (!depends.isNullOrEmpty()) {
            depends.forEach { depend ->
                moduleMap[depend]?.initialize(app)
            }
        }
    }

    override fun unload() {
        modules().forEach { it.unload() }
    }
}