package org.tiramisu.account.data

import org.tiramisu.account.data.model.AccountData
import org.tiramisu.account.data.model.AccountParam
import org.tiramisu.account.data.model.UserData
import org.tiramisu.biz.base.BASE_HTTP_URL
import org.tiramisu.repository.DataException
import org.tiramisu.repository.DataResult
import org.tiramisu.repository.http.BaseHttpCoroutineDataSource

class AccountSource(path: String) : BaseHttpCoroutineDataSource<AccountData, UserData, AccountParam, UserData>(
    baseUrl = BASE_HTTP_URL, path = path, rspClass = UserData::class.java
) {

    private val requestPath = path

    override suspend fun loadData(param: AccountData): DataResult<UserData> {
//        return super.loadData(param)
        return if (requestPath == "sign_in") {
            val data = AccountRepository.readUserDataFromLocal()
            if (data == null) {
                DataResult.error(DataException(-1, "无效用户"))
            } else {
                DataResult.success(data)
            }
        } else if (requestPath == "sign_up") {
            val data = UserData("1234", param.phone,
                "https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo_2x.jpg!0x0.webp",
                "哈哈哈啊哈哈哈哈哈哈")
            AccountRepository.saveUserDataToLocal(data)
            DataResult.success(data)
        } else {
            DataResult.error(DataException(-1, "无效用户"))
        }
    }

    override fun getRequest(param: AccountData): AccountParam = AccountParam(param)

    override fun getResponse(response: UserData): UserData = response
}
