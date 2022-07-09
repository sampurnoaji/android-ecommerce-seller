package id.io.android.olebsai.domain.usecase.user

import id.io.android.olebsai.domain.usecase.GetUserUseCase
import id.io.android.olebsai.domain.usecase.user.login.LoginUseCase
import id.io.android.olebsai.domain.usecase.user.login.LoginWithOtpUseCase
import id.io.android.olebsai.domain.usecase.user.register.RegisterUseCase
import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val setLoggedInUseCase: SetLoggedInUseCase,
    val getIsLoggedInUseCase: GetIsLoggedInUseCase,
    val getUserUseCase: GetUserUseCase,
    val registerUseCase: RegisterUseCase,
    val loginUseCase: LoginUseCase,
    val loginWithOtpUseCase: LoginWithOtpUseCase
)
