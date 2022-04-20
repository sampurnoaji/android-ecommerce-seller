package id.io.android.seller.domain.usecase.user

import id.io.android.seller.domain.usecase.GetUserUseCase
import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val setLoggedInUseCase: SetLoggedInUseCase,
    val getIsLoggedInUseCase: GetIsLoggedInUseCase,
    val getUserUseCase: GetUserUseCase
)
