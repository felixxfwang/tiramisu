package org.tiramisu.account.data

import org.tiramisu.account.data.model.AccountData
import org.tiramisu.account.data.model.UserData
import org.tiramisu.repository.DataResult

class AccountRepository(private val source: AccountSource) {

    // in-memory cache of the loggedInUser object
    var user: UserData? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null
//        dataSource.logout()
    }

    suspend fun doAction(phone: String, password: String): DataResult<UserData> {
        val result = source.loadData(AccountData(phone, password))
        if (result.isSuccess()) {
            this.user = result.get()
            saveUserDataToLocal(result.get())
        }
        return result
    }

    private fun saveUserDataToLocal(loggedInUser: UserData) {

    }
}