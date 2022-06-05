package id.io.android.seller.domain.usecase.user.register

import id.io.android.seller.core.BaseUseCase
import id.io.android.seller.domain.repository.UserRepository
import id.io.android.seller.presentation.user.register.RegisterParams
import id.io.android.seller.util.LoadState
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: UserRepository) :
    BaseUseCase<RegisterParams, LoadState<String>> {
    override suspend fun invoke(params: RegisterParams): LoadState<String> {
        return repository.register(params)
    }
}