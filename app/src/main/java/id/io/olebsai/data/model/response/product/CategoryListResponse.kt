package id.io.olebsai.data.model.response.product


import com.squareup.moshi.Json
import id.io.olebsai.domain.model.product.Category

data class CategoryListResponse(
    @field:Json(name = "data")
    val `data`: List<Data>? = null,
    @field:Json(name = "totalData")
    val totalData: Int? = null
) {
    data class Data(
        @field:Json(name = "gambarIcon")
        val gambarIcon: String? = null,
        @field:Json(name = "kategoriId")
        val kategoriId: String? = null,
        @field:Json(name = "keterangan")
        val keterangan: String? = null,
        @field:Json(name = "namaKategori")
        val namaKategori: String? = null
    ) {
        fun toDomain() = Category(
            gambarIcon = gambarIcon.orEmpty(),
            kategoriId = kategoriId.orEmpty(),
            keterangan = keterangan.orEmpty(),
            namaKategori = namaKategori.orEmpty(),
        )
    }
}