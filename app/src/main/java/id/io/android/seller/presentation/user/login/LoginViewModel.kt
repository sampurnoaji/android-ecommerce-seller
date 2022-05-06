package id.io.android.seller.presentation.user.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.seller.domain.usecase.user.UserUseCases
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {

    var username = ""
    var password = ""

    fun onUsernameChanged(username: String) {
        this.username = username.trim()
    }

    fun onPasswordChanged(password: String) {
        this.password = password.trim()
    }

    fun login() {
        userUseCases.setLoggedInUseCase(true)
    }
}