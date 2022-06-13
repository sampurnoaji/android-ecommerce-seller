package id.io.android.olebsai.data.model.response.product


import com.squareup.moshi.Json
import id.io.android.olebsai.domain.model.product.SubCategory

data class SubCategoryListResponse(
    @Json(name = "data")
    val `data`: List<Data>? = null,
    @Json(name = "totalData")
    val totalData: Int? = null
) {
    data class Data(
        @Json(name = "kategoriId")
        val kategoriId: String? = null,
        @Json(name = "keterangan")
        val keterangan: String? = null,
        @Json(name = "namaKategori")
        val namaKategori: String? = null,
        @Json(name = "namaSubKategori")
        val namaSubKategori: String? = null,
        @Json(name = "subKategoriId")
        val subKategoriId: String? = null
    ) {
        fun toDomain() = SubCategory(
            kategoriId = kategoriId.orEmpty(),
            keterangan = keterangan.orEmpty(),
            namaKategori = namaKategori.orEmpty(),
            namaSubKategori = namaSubKategori.orEmpty(),
            subKategoriId = subKategoriId.orEmpty(),
        )
    }
}