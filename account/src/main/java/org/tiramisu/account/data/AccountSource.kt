package org.tiramisu.account.data

import org.tiramisu.account.data.model.AccountData
import org.tiramisu.account.data.model.AccountParam
import org.tiramisu.account.data.model.UserData
import org.tiramisu.biz.base.BASE_HTTP_URL
import org.tiramisu.repository.http.BaseHttpCoroutineDataSource

class AccountSource(path: String) : BaseHttpCoroutineDataSource<AccountData, UserData, AccountParam, UserData>(
    baseUrl = BASE_HTTP_URL, path = path, rspClass = UserData::class.java
) {
    override fun getRequest(param: AccountData): AccountParam = AccountParam(param)

    override fun getResponse(response: UserData): UserData = response
}
