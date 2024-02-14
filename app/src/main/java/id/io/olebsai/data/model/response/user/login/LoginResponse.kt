package id.io.olebsai.data.model.response.user.login


import com.squareup.moshi.Json
import id.io.olebsai.domain.model.user.User

data class LoginResponse(
    @field:Json(name = "email")
    val email: String? = null,
    @field:Json(name = "nama")
    val nama: String? = null,
    @field:Json(name = "role")
    val role: String? = null,
    @field:Json(name = "token")
    val token: String? = null,
    @field:Json(name = "otpFlag")
    val otpFlag: Boolean? = null,
) {
    fun toDomain() = User(
        id = "",
        username = nama.orEmpty(),
        email = email.orEmpty(),
        otpFlag = otpFlag ?: false,
    )
}