package org.tiramisu.account.data.model

import org.tiramisu.http.HttpParam

class AccountParam(private val data: AccountData) : HttpParam() {

    override fun toMap(): Map<String, Any> {
        return mapOf("phone" to data.phone, "password" to data.password)
    }
}