package id.io.android.olebsai.domain.usecase.user

import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(): User? = repository.getUser()
}