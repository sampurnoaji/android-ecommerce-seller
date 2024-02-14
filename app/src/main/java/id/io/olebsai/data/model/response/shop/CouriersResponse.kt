package id.io.olebsai.data.model.response.shop


import com.squareup.moshi.Json
import id.io.olebsai.domain.model.shop.Courier

data class CouriersResponse(
    @field:Json(name = "data")
    val data: List<CourierResponse>
) {
    data class CourierResponse(
        @field:Json(name = "gambarLogo")
        val gambarLogo: Any? = null,
        @field:Json(name = "kodeKurir")
        val kodeKurir: String? = null,
        @field:Json(name = "kurirId")
        val kurirId: String? = null,
        @field:Json(name = "namaKurir")
        val namaKurir: String? = null,
    ) {
        fun toDomain() = Courier(
            gambarLogo = gambarLogo.toString(),
            kodeKurir = kodeKurir.orEmpty(),
            kurirId = kurirId.orEmpty(),
            namaKurir = namaKurir.orEmpty()
        )
    }
}