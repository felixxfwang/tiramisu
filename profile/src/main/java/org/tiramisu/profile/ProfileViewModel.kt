package org.tiramisu.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    val userData = MutableLiveData<UserData>()

    fun loadUserData() {
        userData.value = UserData("珞小飞", "https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo_2x.jpg!0x0.webp", "哈哈哈哈哈哈哈哈")
    }
}
