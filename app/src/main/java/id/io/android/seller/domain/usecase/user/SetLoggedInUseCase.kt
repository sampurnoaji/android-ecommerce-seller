package id.io.android.seller.domain.usecase.user

import id.io.android.seller.domain.repository.UserRepository
import javax.inject.Inject

class SetLoggedInUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(isLoggedIn: Boolean) = repository.setLoggedIn(isLoggedIn)
}