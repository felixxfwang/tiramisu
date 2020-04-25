package org.tiramisu.account.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.tiramisu.account.data.AccountRepository
import org.tiramisu.account.data.AccountSource

class AccountViewModelFactory(private val path: String) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(
                repository = AccountRepository(AccountSource(path))
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}