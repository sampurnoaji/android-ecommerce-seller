package id.io.android.olebsai.presentation.user.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.usecase.user.UserUseCases
import id.io.android.olebsai.util.LoadState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {

    var email = ""
    var name = ""
    var phoneNumber = ""
    var shopName = ""
    var password = ""
    var repeatPassword = ""

    private val _register = MutableLiveData<LoadState<String>>()
    val register: LiveData<LoadState<String>>
        get() = _register

    fun onEmailChanged(email: String) {
        this.email = email.trim()
    }

    fun onNameChanged(name: String) {
        this.name = name.trim()
    }

    fun onPhoneNumberChanged(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }

    fun onShopNameChanged(shopName: String) {
        this.shopName = shopName
    }

    fun onPasswordChanged(password: String) {
        this.password = password.trim()
    }

    fun onRepeatPasswordChanged(password: String) {
        this.repeatPassword = password.trim()
    }

    fun register() {
        _register.value = LoadState.Loading
        viewModelScope.launch {
            _register.value = userUseCases.registerUseCase(
                RegisterParams(
                    name = name,
                    email = email,
                    phoneNumber = phoneNumber,
                    shopName = shopName,
                    password = password
                )
            )
        }
    }
}