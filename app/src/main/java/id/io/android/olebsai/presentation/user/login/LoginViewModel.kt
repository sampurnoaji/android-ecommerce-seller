package id.io.android.olebsai.presentation.user.login

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
class LoginViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {

    var email = ""
    var password = ""
    var otp = ""

    private val _login = MutableLiveData<LoadState<String>>()
    val login: LiveData<LoadState<String>>
        get() = _login

    private val _loginWithOtp = MutableLiveData<LoadState<String>>()
    val loginWithOtp: LiveData<LoadState<String>>
        get() = _loginWithOtp

    fun onEmailChanged(email: String) {
        this.email = email.trim()
    }

    fun onPasswordChanged(password: String) {
        this.password = password.trim()
    }

    fun login() {
        _login.value = LoadState.Loading
        viewModelScope.launch {
            _login.value = userUseCases.loginUseCase(
                LoginParams(
                    email = email,
                    password = password
                )
            )
        }
    }

    fun loginWithOtp() {
        _loginWithOtp.value = LoadState.Loading
        viewModelScope.launch {
            _loginWithOtp.value = userUseCases.loginWithOtpUseCase(
                LoginParams(
                    email = email,
                    otp = otp
                )
            )
        }
    }
}