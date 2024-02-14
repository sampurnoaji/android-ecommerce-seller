package id.io.olebsai.domain.usecase.user.landing

import id.io.olebsai.domain.repository.UserRepository
import javax.inject.Inject

class GetIsFirstLaunchAppUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke() = repository.isFirstLaunchApp()
}