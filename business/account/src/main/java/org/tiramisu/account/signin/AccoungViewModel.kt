package org.tiramisu.account.signin

import android.util.Patterns
import androidx.lifecycle.*
import kotlinx.coroutines.launch

import org.tiramisu.account.R
import org.tiramisu.account.data.AccountRepository
import org.tiramisu.account.data.model.UserData
import org.tiramisu.repository.DataResult

typealias LoginResult = DataResult<UserData>

class AccountViewModel(private val repository: AccountRepository) : ViewModel() {

    private val _form = MutableLiveData<AccountFormState>()
    val formState: LiveData<AccountFormState> = _form

    private val _result = MutableLiveData<LoginResult>()
    val result: LiveData<LoginResult> = _result

    fun doAccountAction(username: String, password: String) {
        viewModelScope.launch {
            _result.value = repository.doAction(username, password)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isPhoneValid(username)) {
            _form.value = AccountFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _form.value = AccountFormState(passwordError = R.string.invalid_password)
        } else {
            _form.value = AccountFormState(isDataValid = true)
        }
    }

    private fun isPhoneValid(phone: String): Boolean {
        return phone.isNotBlank() && Patterns.PHONE.matcher(phone).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 6
    }
}