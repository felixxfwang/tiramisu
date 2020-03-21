package org.tiramisu.network.service

abstract class HttpParam {

    abstract fun toMap(): Map<String, Any>
}