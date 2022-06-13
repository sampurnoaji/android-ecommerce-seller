package id.io.android.olebsai.domain.model.product

import id.io.android.olebsai.data.model.request.product.CreateProductRequest

data class CreateProductParams(
    val beratGram: Int,
    val deskripsi: String,
    val hargaNormal: Long,
    val hargaPromo: Long,
    val kategoriId: String,
    val namaProduk: String,
    val qtyStock: Int,
    val subKategoriId: String,
    val tokoId: String
) {

    fun toRequest() = CreateProductRequest(
        deskripsi = deskripsi,
        hargaNormal = hargaNormal,
        hargaPromo = hargaPromo,
        kategoriId = kategoriId,
        namaProduk = namaProduk,
        qtyStock = qtyStock,
        subKategoriId = subKategoriId,
        tokoId = tokoId,
        beratGram = beratGram,
    )
}
