package id.io.olebsai.domain.usecase.user

import id.io.olebsai.domain.model.user.User
import id.io.olebsai.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(): User? = repository.getUser()
}