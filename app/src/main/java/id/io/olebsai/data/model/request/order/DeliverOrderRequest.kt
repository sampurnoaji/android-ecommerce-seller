package id.io.olebsai.data.model.request.order


import com.squareup.moshi.Json

data class DeliverOrderRequest(
    @field:Json(name = "nomorResi")
    val nomorResi: String,
    @field:Json(name = "pesananHeaderId")
    val pesananHeaderId: String,
)