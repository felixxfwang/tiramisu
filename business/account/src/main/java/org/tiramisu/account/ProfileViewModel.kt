package org.tiramisu.account

import android.content.Intent
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.tiramisu.account.Constants.KEY_USER_DATA
import org.tiramisu.base.appContext
import org.tiramisu.biz.base.DataConstants
import org.tiramisu.data.user.User
import org.tiramisu.feeds.data.ChannelModel
import org.tiramisu.feeds.data.IChannelModel

class ProfileViewModel(
    private val repository: ProfileRepository = ProfileRepository()
) : ViewModel() {

    companion object {
        private val NO_USER = User("0", "未登录", "", "请点击注册")
    }

    private val _userData = MutableLiveData<User>()
    private val userData: LiveData<User> = _userData
    val username: LiveData<String> = Transformations.map(userData) { it.username }
    val signature: LiveData<String> = Transformations.map(userData) { it.signature }
    val avatarUrl: LiveData<String> = Transformations.map(userData) { it.avatar }

    val channels: LiveData<List<IChannelModel>> = MutableLiveData<List<IChannelModel>>(listOf(
        ChannelModel(appContext.getString(R.string.collect), DataConstants.CHANNEL_PROFILE),
        ChannelModel(appContext.getString(R.string.history), DataConstants.CHANNEL_PROFILE),
        ChannelModel(appContext.getString(R.string.my_works), DataConstants.CHANNEL_PROFILE)
    ))

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
