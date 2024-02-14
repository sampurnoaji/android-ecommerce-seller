package id.io.olebsai.data.model.response.order


import com.squareup.moshi.Json

data class ActiveOrderResponse(
    @field:Json(name = "data")
    val `data`: List<OrderResponse>
)