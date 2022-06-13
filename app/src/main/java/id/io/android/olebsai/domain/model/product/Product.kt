package id.io.android.olebsai.domain.model.product

import kotlin.math.roundToInt

data class Product(
    val approvalId: String,
    val deskripsi: String,
    val hargaNormal: Long,
    val hargaPromo: Long,
    val kategoriId: String,
    val namaKategori: String,
    val namaProduk: String,
    val produkId: String,
    val status: ApprovalStatus,
    val subKategoriId: String,
    val namaSubKategori: String,
    val tokoId: String,
    val isHargaPromo: Boolean,
    val qtyStock: Int,
    val qtyTerjual: Int,
    val approvedBy: Any,
    val listPicture: List<Picture>,
    val namaToko: String,
    val beratGram: Int,
    var isUpdatingProgress: Boolean = false,
) {
    data class Picture(
        val namaAlias: String,
        val parentId: String,
        val parentType: String,
        val pictureId: String,
        val url: String
    )
    enum class ApprovalStatus(val status: String) {
        REVIEW("REGISTRASI"),
        APPROVE("AKTIF"),
        REJECTED("REJECTED"),
        EDIT("EDIT"),
    }

    fun discount(): Int = 100 - (hargaPromo.toFloat() / hargaNormal * 100).roundToInt()
}
