package id.io.olebsai.presentation.user.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.olebsai.domain.model.user.User
import id.io.olebsai.domain.usecase.user.UserUseCases
import id.io.olebsai.presentation.user.login.LoginParams
import id.io.olebsai.util.LoadState
import id.io.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class OtpViewModel @Inject constructor(private val userUseCases: UserUseCases): ViewModel() {

    private val _loginWithOtpResult = SingleLiveEvent<LoadState<String>>()
    val loginWithOtpResult: LiveData<LoadState<String>>
        get() = _loginWithOtpResult

    var otp = ""

    fun onOtpChanged(otp: String) {
        this.otp = otp
    }

    fun loginWithOtp(email: String) {
        _loginWithOtpResult.value = LoadState.Loading
        viewModelScope.launch {
            _loginWithOtpResult.value = userUseCases.loginWithOtpUseCase(
                LoginParams(
                    email = email,
                    otp = otp
                )
            )
        }
    }

    fun saveUser(user: User) {
        userUseCases.saveUserUseCase(user)
    }
}