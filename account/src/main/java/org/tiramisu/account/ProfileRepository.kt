package org.tiramisu.account

import org.tiramisu.account.data.AccountRepository
import org.tiramisu.account.data.model.UserData

class ProfileRepository {

    private var user: UserData? = null

    suspend fun getUserData(): UserData? {
        return user ?: AccountRepository.readUserDataFromLocal().also { user = it }
    }
}