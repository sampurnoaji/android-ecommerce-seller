package id.io.android.olebsai.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.domain.usecase.user.UserUseCases
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.LoadStateUtil
import id.io.android.olebsai.util.NoParams
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {

    val isLoggedIn: Boolean get() = userUseCases.getIsLoggedInUseCase()

    val user: LiveData<LoadState<User>> = liveData {
        emit(LoadState.Loading)
        emit(LoadStateUtil.map(userUseCases.getUserUseCase(NoParams)))
    }

    fun logout() {
        userUseCases.setLoggedInUseCase(false)
    }
}