package id.io.android.seller.domain.usecase.user

import id.io.android.seller.domain.repository.UserRepository
import javax.inject.Inject

class GetIsLoggedInUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke() = repository.isLoggedIn()
}