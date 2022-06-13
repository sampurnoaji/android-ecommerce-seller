package id.io.android.olebsai.presentation.user.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.R
import id.io.android.olebsai.domain.usecase.user.UserUseCases
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {

    val user = userUseCases.getUserUseCase()

    val images = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3,
        R.drawable.banner4,
        R.drawable.banner5,
    )

    fun logout() {
        userUseCases.setLoggedInUseCase(false)
    }
}