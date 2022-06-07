package id.io.android.olebsai.domain.usecase.user.register

import id.io.android.olebsai.core.BaseUseCase
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.presentation.user.register.RegisterParams
import id.io.android.olebsai.util.LoadState
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: UserRepository) :
    BaseUseCase<RegisterParams, LoadState<String>> {
    override suspend fun invoke(params: RegisterParams): LoadState<String> {
        return repository.register(params)
    }
}