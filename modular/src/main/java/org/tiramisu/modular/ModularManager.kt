package org.tiramisu.modular

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
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
        initARouter(application)
        modules().forEach { module -> initModule(module, application) }
    }

    private fun initARouter(app: Application) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(app)
    }

    private fun initModule(module: IModule, app: Application) {
        if (!module.isInitialized()) {
            checkDependsInitialize(module, app)
            module.initialize(app)
        }
    }

    private fun checkDependsInitialize(module: IModule, app: Application) {
        val depends = module.dependsOn()
        if (depends.isNotEmpty()) {
            depends.forEach { depend ->
                moduleMap[depend]?.let { initModule(it, app) }
            }
        }
    }

    override fun unload() {
        modules().forEach { it.unload() }
    }
}