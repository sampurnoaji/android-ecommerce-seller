package id.io.olebsai.domain.usecase.user

import id.io.olebsai.domain.model.user.User
import id.io.olebsai.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(user: User) = repository.saveUser(user)
}