package id.io.android.seller.presentation.user.register

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.seller.domain.usecase.user.UserUseCases
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {

    fun login() {
        userUseCases.setLoggedInUseCase(true)
    }
}