package org.tiramisu.account.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.tiramisu.account.data.model.AccountData
import org.tiramisu.account.data.model.UserData
import org.tiramisu.base.appContext
import org.tiramisu.repository.DataResult
import org.tiramisu.serializable.TiramisuSerializer
import java.io.File

class AccountRepository(private val source: AccountSource) {

    companion object {
        private const val ACCOUNT_SAVE_PATH = "account_data"

        private fun getSaveFile() = File(appContext.filesDir, ACCOUNT_SAVE_PATH)

        suspend fun readUserDataFromLocal(): UserData? {
            return withContext(Dispatchers.IO) {
                TiramisuSerializer.readFromFile(getSaveFile(), UserData.serializer)
            }
        }

        suspend fun saveUserDataToLocal(loggedInUser: UserData) {
            withContext(Dispatchers.IO) {
                TiramisuSerializer.writeToFile(
                    File(appContext.filesDir, ACCOUNT_SAVE_PATH),
                    loggedInUser, UserData.serializer
                )
            }
        }
    }

    var user: UserData? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

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
}