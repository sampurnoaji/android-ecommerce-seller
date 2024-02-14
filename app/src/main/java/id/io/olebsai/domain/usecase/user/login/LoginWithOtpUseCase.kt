package id.io.olebsai.domain.usecase.user.login

import id.io.olebsai.core.UseCase
import id.io.olebsai.domain.repository.UserRepository
import id.io.olebsai.presentation.user.login.LoginParams
import id.io.olebsai.util.LoadState
import javax.inject.Inject

class LoginWithOtpUseCase @Inject constructor(private val repository: UserRepository) :
    UseCase<LoginParams, LoadState<String>> {

    override suspend fun invoke(params: LoginParams): LoadState<String> {
        val result = repository.loginWithOtp(params)
        if (result is LoadState.Success) {
            repository.setLoggedIn(true)
            repository.setToken(result.data)
        }
        return result
    }
}