package id.io.android.olebsai.data.model.request.product


import com.squareup.moshi.Json

data class RegisterImageRequest(
    @field:Json(name = "extension")
    val extension: String,
    @field:Json(name = "namaFile")
    val namaFile: String,
    @field:Json(name = "parentId")
    val parentId: String,
    @field:Json(name = "parentType")
    val parentType: String,
    @field:Json(name = "url")
    val url: String
)