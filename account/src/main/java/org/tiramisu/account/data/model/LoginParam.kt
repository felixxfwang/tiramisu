package org.tiramisu.account.data.model

import org.tiramisu.http.HttpParam

class LoginParam(private val data: LoginData) : HttpParam() {

    override fun toMap(): Map<String, Any> {
        return mapOf("username" to data.username, "password" to data.password)
    }
}