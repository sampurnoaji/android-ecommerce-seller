package id.io.android.olebsai.domain.usecase.user

import id.io.android.olebsai.domain.usecase.user.landing.GetIsFirstLaunchAppUseCase
import id.io.android.olebsai.domain.usecase.user.landing.SetFirstLaunchAppUseCase
import id.io.android.olebsai.domain.usecase.user.login.GetIsLoggedInUseCase
import id.io.android.olebsai.domain.usecase.user.login.LoginUseCase
import id.io.android.olebsai.domain.usecase.user.login.LoginWithOtpUseCase
import id.io.android.olebsai.domain.usecase.user.login.SetLoggedInUseCase
import id.io.android.olebsai.domain.usecase.user.register.RegisterUseCase
import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val setFirstLaunchAppUseCase: SetFirstLaunchAppUseCase,
    val getIsFirstLaunchAppUseCase: GetIsFirstLaunchAppUseCase,
    val setLoggedInUseCase: SetLoggedInUseCase,
    val getIsLoggedInUseCase: GetIsLoggedInUseCase,
    val saveUserUseCase: SaveUserUseCase,
    val getUserUseCase: GetUserUseCase,
    val registerUseCase: RegisterUseCase,
    val loginUseCase: LoginUseCase,
    val loginWithOtpUseCase: LoginWithOtpUseCase
)
