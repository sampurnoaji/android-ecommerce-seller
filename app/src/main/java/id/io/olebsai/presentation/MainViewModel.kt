package id.io.olebsai.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.olebsai.domain.usecase.user.UserUseCases
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {

    fun isFirstLaunchApp(): Boolean = userUseCases.getIsFirstLaunchAppUseCase()
    fun isLoggedIn(): Boolean = userUseCases.getIsLoggedInUseCase()

    private val _newOrders = MutableLiveData<Int>()
    val newOrders: LiveData<Int>
        get() = _newOrders

    init {
        _newOrders.value = 1
    }

    fun setFirstLaunchAppTrue() {
        userUseCases.setFirstLaunchAppUseCase(false)
    }
}