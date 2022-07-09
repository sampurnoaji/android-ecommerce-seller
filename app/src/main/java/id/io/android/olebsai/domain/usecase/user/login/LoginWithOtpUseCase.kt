package id.io.android.olebsai.domain.usecase.user.login

import id.io.android.olebsai.core.BaseUseCase
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.presentation.user.login.LoginParams
import id.io.android.olebsai.util.LoadState
import javax.inject.Inject

class LoginWithOtpUseCase @Inject constructor(private val repository: UserRepository) :
    BaseUseCase<LoginParams, LoadState<String>> {
    override suspend fun invoke(params: LoginParams): LoadState<String> {
        val result = repository.loginWithOtp(params)
        if (result is LoadState.Success) {
            repository.setLoggedIn(true)
            repository.setToken(result.data)
        }
        return result
    }
}