package org.tiramisu.account.data

import org.tiramisu.account.data.model.LoginData
import org.tiramisu.account.data.model.LoginParam
import org.tiramisu.account.data.model.UserData
import org.tiramisu.biz.base.BASE_HTTP_URL
import org.tiramisu.repository.*
import org.tiramisu.repository.http.HttpDataClient

class LoginSource : BaseCoroutineSource<LoginData, UserData, LoginParam, UserData>() {

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

    suspend fun login(username: String, password: String): DataResult<UserData> {
        val result = loadData(LoginData(username, password))
        if (result.isSuccess()) {
            this.user = result.get()
            saveUserDataToLocal(result.get())
        }
        return result
    }

    private fun saveUserDataToLocal(loggedInUser: UserData) {

    }

    override fun getDataClient(): DataClient<LoginParam, UserData> {
        return HttpDataClient(BASE_HTTP_URL, "login", UserData::class.java)
    }

    override fun getRequest(param: LoginData): LoginParam = LoginParam(param)

    override fun getResponse(response: UserData): UserData = response
}
