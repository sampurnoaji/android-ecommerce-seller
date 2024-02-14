package id.io.olebsai.presentation.user.register

data class RegisterParams(
    val email: String,
    val name: String,
    val phoneNumber: String,
    val shopName: String,
    val password: String
)
