package id.io.olebsai.domain.usecase.user.login

import id.io.olebsai.core.UseCase
import id.io.olebsai.domain.model.user.User
import id.io.olebsai.domain.repository.UserRepository
import id.io.olebsai.presentation.user.login.LoginParams
import id.io.olebsai.util.LoadState
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: UserRepository) :
    UseCase<LoginParams, LoadState<Pair<User, String>>> {

    override suspend fun invoke(params: LoginParams): LoadState<Pair<User, String>> {
        val result = repository.login(params)
        if (result is LoadState.Success) {
            repository.saveUser(result.data.first)
            repository.setLoggedIn(true)
            repository.setToken(result.data.second)
        }
        return result
    }
}