package id.io.android.olebsai.domain.usecase.user.login

import id.io.android.olebsai.domain.repository.UserRepository
import javax.inject.Inject

class SetLoggedInUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(isLoggedIn: Boolean) = repository.setLoggedIn(isLoggedIn)
}