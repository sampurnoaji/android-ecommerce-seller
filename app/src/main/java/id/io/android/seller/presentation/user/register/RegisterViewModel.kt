package id.io.android.seller.presentation.user.register

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    var username = ""
    var shopName = ""
    var password = ""

    fun onUsernameChanged(username: String) {
        this.username = username.trim()
    }

    fun onShopNameChanged(shopName: String) {
        this.shopName = shopName.trim()
    }

    fun onPasswordChanged(password: String) {
        this.password = password.trim()
    }
}