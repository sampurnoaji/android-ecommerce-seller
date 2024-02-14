package id.io.olebsai.domain.usecase.user.register

import id.io.olebsai.core.UseCase
import id.io.olebsai.domain.repository.UserRepository
import id.io.olebsai.presentation.user.register.RegisterParams
import id.io.olebsai.util.LoadState
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: UserRepository) :
    UseCase<RegisterParams, LoadState<String>> {

    override suspend fun invoke(params: RegisterParams): LoadState<String> {
        return repository.register(params)
    }
}