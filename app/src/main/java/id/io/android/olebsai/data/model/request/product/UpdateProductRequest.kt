package id.io.android.olebsai.data.model.request.product


import com.squareup.moshi.Json

data class UpdateProductRequest(
    @field:Json(name = "beratGram")
    val beratGram: Int,
    @field:Json(name = "deskripsi")
    val deskripsi: String,
    @field:Json(name = "hargaNormal")
    val hargaNormal: Long,
    @field:Json(name = "hargaPromo")
    val hargaPromo: Long,
    @field:Json(name = "isHargaPromo")
    val isHargaPromo: Boolean,
    @field:Json(name = "kategoriId")
    val kategoriId: String,
    @field:Json(name = "namaProduk")
    val namaProduk: String,
    @field:Json(name = "produkId")
    val produkId: String,
    @field:Json(name = "qtyStock")
    val qtyStock: Int,
    @field:Json(name = "subKategoriId")
    val subKategoriId: String
)