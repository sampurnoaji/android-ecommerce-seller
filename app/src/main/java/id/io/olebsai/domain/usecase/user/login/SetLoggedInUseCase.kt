package id.io.olebsai.domain.usecase.user.login

import id.io.olebsai.domain.repository.UserRepository
import javax.inject.Inject

class SetLoggedInUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(isLoggedIn: Boolean) = repository.setLoggedIn(isLoggedIn)
}