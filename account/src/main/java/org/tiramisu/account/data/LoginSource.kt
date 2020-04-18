package org.tiramisu.account.data

import org.tiramisu.account.data.model.LoginData
import org.tiramisu.account.data.model.LoginParam
import org.tiramisu.account.data.model.UserData
import org.tiramisu.biz.base.BASE_HTTP_URL
import org.tiramisu.repository.*
import org.tiramisu.repository.coroutine.BaseCoroutineDataSource
import org.tiramisu.repository.coroutine.CoroutineDataClient
import org.tiramisu.repository.http.BaseHttpCoroutineDataSource
import org.tiramisu.repository.http.HttpCoroutineDataClient
import org.tiramisu.repository.http.HttpDataClient

class LoginSource : BaseHttpCoroutineDataSource<LoginData, UserData, LoginParam, UserData>(
    baseUrl = BASE_HTTP_URL, path = "login", rspClass = UserData::class.java
) {
    override fun getRequest(param: LoginData): LoginParam = LoginParam(param)

    override fun getResponse(response: UserData): UserData = response
}
