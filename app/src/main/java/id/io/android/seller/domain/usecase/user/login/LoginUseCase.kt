package id.io.android.seller.domain.usecase.user.login

import id.io.android.seller.core.BaseUseCase
import id.io.android.seller.domain.repository.UserRepository
import id.io.android.seller.presentation.user.login.LoginParams
import id.io.android.seller.util.LoadState
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: UserRepository) :
    BaseUseCase<LoginParams, LoadState<String>> {
    override suspend fun invoke(params: LoginParams): LoadState<String> {
        val result = repository.login(params)
        if (result is LoadState.Success) {
            repository.setLoggedIn(true)
        }
        return result
    }
}