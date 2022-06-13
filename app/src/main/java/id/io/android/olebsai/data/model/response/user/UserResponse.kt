package id.io.android.olebsai.data.model.response.user


import com.squareup.moshi.Json

data class UserResponse(
    @field:Json(name = "data")
    val `data`: Data? = null,
    @field:Json(name = "support")
    val support: Support? = null
) {
    data class Data(
        @field:Json(name = "avatar")
        val avatar: String? = null,
        @field:Json(name = "email")
        val email: String? = null,
        @field:Json(name = "first_name")
        val firstName: String? = null,
        @field:Json(name = "id")
        val id: Int? = null,
        @field:Json(name = "last_name")
        val lastName: String? = null
    )

    data class Support(
        @field:Json(name = "text")
        val text: String? = null,
        @field:Json(name = "url")
        val url: String? = null
    )
}