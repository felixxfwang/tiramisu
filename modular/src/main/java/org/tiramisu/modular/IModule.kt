package org.tiramisu.modular

import android.app.Application

interface IModule {

    /**
     * 模块名称
     */
    fun name(): String

    /**
     * 模块初始化
     */
    fun initialize(application: Application)

    /**
     * 模块反初始化
     */
    fun unload() {}
}