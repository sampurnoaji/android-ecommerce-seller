package id.io.olebsai.domain.usecase.user.login

import id.io.olebsai.domain.repository.UserRepository
import javax.inject.Inject

class GetIsLoggedInUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke() = repository.isLoggedIn()
}