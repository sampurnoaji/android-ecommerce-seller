package id.io.android.olebsai.presentation.user.account

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.usecase.user.UserUseCases
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val userUseCases: UserUseCases): ViewModel() {

    fun logout() {
        userUseCases.setLoggedInUseCase(false)
    }
}