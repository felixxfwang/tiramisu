package org.tiramisu.account

import org.tiramisu.account.data.AccountRepository
import org.tiramisu.data.user.User

class ProfileRepository {

    private var user: User? = null

    suspend fun getUserData(): User? {
        return user ?: AccountRepository.readUserDataFromLocal().also { user = it }
    }
}