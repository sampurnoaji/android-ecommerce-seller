package id.io.android.olebsai.presentation.user.login

data class LoginParams(
    val email: String,
    val password: String? = null,
    val otp: String? = null
)
