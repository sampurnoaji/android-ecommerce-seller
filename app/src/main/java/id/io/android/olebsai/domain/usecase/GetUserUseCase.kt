package id.io.android.olebsai.domain.usecase

import id.io.android.olebsai.core.BaseUseCase
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.NoParams
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserRepository): BaseUseCase<NoParams, User> {
    override suspend fun invoke(params: NoParams): User = repository.getUser(2)
}