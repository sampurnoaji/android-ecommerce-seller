package id.io.olebsai.presentation.user.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.olebsai.domain.model.user.User
import id.io.olebsai.domain.usecase.user.UserUseCases
import id.io.olebsai.util.LoadState
import id.io.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {

    var username = ""
    var password = ""
    var otp = ""

    private val _isEmptyForm = MutableLiveData(false)
    val isEmptyForm: LiveData<Boolean>
        get() = _isEmptyForm

    private val _isErrorForm = MutableLiveData(false)
    val isErrorForm: LiveData<Boolean>
        get() = _isErrorForm

    private val _login = SingleLiveEvent<LoadState<Pair<User, String>>>()
    val login: LiveData<LoadState<Pair<User, String>>>
        get() = _login

    fun onUsernameChanged(email: String) {
        this.username = email.trim()
        _isEmptyForm.value = false
        _isErrorForm.value = false
    }

    fun onPasswordChanged(password: String) {
        this.password = password.trim()
        _isEmptyForm.value = false
        _isErrorForm.value = false
    }

    fun login() {
        if (username.isEmpty() || password.isEmpty()) {
            _isEmptyForm.value = true
            return
        }

        _login.value = LoadState.Loading
        viewModelScope.launch {
            userUseCases.setLoggedInUseCase(true)
            _login.value = userUseCases.loginUseCase(
                LoginParams(
                    email = username,
                    password = password
                )
            )
        }
    }
}