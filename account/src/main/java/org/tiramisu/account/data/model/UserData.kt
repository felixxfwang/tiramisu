package org.tiramisu.account.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class UserData(
    val userId: String,
    val username: String,
    var avatar: String,
    var signature: String
)
