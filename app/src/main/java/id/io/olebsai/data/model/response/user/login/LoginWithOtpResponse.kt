package id.io.olebsai.data.model.response.user.login


import com.squareup.moshi.Json

data class LoginWithOtpResponse(
    @field:Json(name = "token")
    val token: String? = null
)