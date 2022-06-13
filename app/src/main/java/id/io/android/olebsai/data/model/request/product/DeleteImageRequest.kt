package id.io.android.olebsai.data.model.request.product


import com.squareup.moshi.Json

data class DeleteImageRequest(
    @field:Json(name = "pictureId")
    val pictureId: String,
)