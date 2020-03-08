package org.tiramisu.modular

import android.app.Application

interface IModule {

    /**
     * 组件名称
     */
    fun name(): String

    /**
     * 初始化组件
     */
    fun initialize(application: Application)

    /**
     * 反初始化组件
     */
    fun unload() {}

    /**
     * 是否完成了初始化
     */
    fun isInitialized(): Boolean = false

    /**
     * 初始化所依赖的组件名列表
     */
    fun dependsOn(): List<String>? = null
}