package id.io.olebsai.data.model.request.product


import com.squareup.moshi.Json

data class DirectUpdateProductRequest(
    @field:Json(name = "beratGram")
    val beratGram: Int? = null,
    @field:Json(name = "hargaNormal")
    val hargaNormal: Long? = null,
    @field:Json(name = "hargaPromo")
    val hargaPromo: Long? = null,
    @field:Json(name = "isHargaPromo")
    val isHargaPromo: Boolean? = null,
    @field:Json(name = "produkId")
    val produkId: String? = null,
    @field:Json(name = "qtyStock")
    val qtyStock: Int? = null
)