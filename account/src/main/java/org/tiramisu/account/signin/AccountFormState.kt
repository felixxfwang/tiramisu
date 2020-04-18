package org.tiramisu.account.signin

/**
 * Data validation state of the login form.
 */
data class AccountFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)
