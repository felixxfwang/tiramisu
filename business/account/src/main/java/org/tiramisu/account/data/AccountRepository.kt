package org.tiramisu.account.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.tiramisu.account.data.model.AccountData
import org.tiramisu.base.appContext
import org.tiramisu.data.user.User
import org.tiramisu.repository.DataResult
import org.tiramisu.serializable.TiramisuSerializer
import java.io.File

class AccountRepository(private val source: AccountSource) {

    companion object {
        private const val ACCOUNT_SAVE_PATH = "account_data"

        private fun getSaveFile() = File(appContext.filesDir, ACCOUNT_SAVE_PATH)

        suspend fun readUserDataFromLocal(): User? {
            return withContext(Dispatchers.IO) {
                TiramisuSerializer.readFromFile(getSaveFile(), User.serializer)
            }
        }

        suspend fun saveUserDataToLocal(loggedInUser: User) {
            withContext(Dispatchers.IO) {
                TiramisuSerializer.writeToFile(
                    File(appContext.filesDir, ACCOUNT_SAVE_PATH),
                    loggedInUser, User.serializer
                )
            }
        }
    }

    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    fun logout() {
        user = null
//        dataSource.logout()
    }

    suspend fun doAction(phone: String, password: String): DataResult<User> {
        val result = source.loadData(AccountData(phone, password))
        if (result.isSuccess()) {
            this.user = result.get()
            saveUserDataToLocal(result.get())
        }
        return result
    }
}