package id.io.olebsai.data.model.request.product


import com.squareup.moshi.Json

data class DeleteProductRequest(
    @field:Json(name = "produkId")
    val produkId: String
)