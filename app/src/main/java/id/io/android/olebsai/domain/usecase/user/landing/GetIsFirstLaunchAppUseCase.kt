package id.io.android.olebsai.domain.usecase.user.landing

import id.io.android.olebsai.domain.repository.UserRepository
import javax.inject.Inject

class GetIsFirstLaunchAppUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke() = repository.isFirstLaunchApp()
}