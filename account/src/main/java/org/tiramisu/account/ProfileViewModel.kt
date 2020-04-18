package org.tiramisu.account

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.tiramisu.account.Constants.KEY_USER_DATA
import org.tiramisu.account.data.model.UserData

class ProfileViewModel(
    private val repository: ProfileRepository = ProfileRepository()
) : ViewModel() {

    companion object {
        private val NO_USER = UserData("0", "未登录", "", "请点击注册")
    }

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    fun loadUserData() {
        viewModelScope.launch {
            _userData.value = repository.getUserData() ?: NO_USER
        }
    }

    fun isSignedIn(): Boolean {
        return userData.value != null && userData.value != NO_USER
    }

    fun onSignedIn(data: Intent?) {
        _userData.value = data?.getParcelableExtra(KEY_USER_DATA)
    }
}
