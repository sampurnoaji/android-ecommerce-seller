package id.io.android.seller.data.model.response.user.login


import com.squareup.moshi.Json

data class LoginResponse(
    @field:Json(name = "data")
    val `data`: Data? = null
) {
    data class Data(
        @field:Json(name = "email")
        val email: String? = null,
        @field:Json(name = "nama")
        val nama: String? = null,
        @field:Json(name = "role")
        val role: String? = null,
        @field:Json(name = "token")
        val token: String? = null
    )
}