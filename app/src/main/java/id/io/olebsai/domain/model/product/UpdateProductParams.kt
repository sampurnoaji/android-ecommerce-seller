package id.io.olebsai.domain.model.product

import id.io.olebsai.data.model.request.product.DirectUpdateProductRequest
import id.io.olebsai.data.model.request.product.UpdateProductRequest

data class UpdateProductParams(
    val beratGram: Int,
    val deskripsi: String,
    val hargaNormal: Long,
    val hargaPromo: Long,
    val isHargaPromo: Boolean,
    val kategoriId: String,
    val namaProduk: String,
    val qtyStock: Int,
    val subKategoriId: String,
    val produkId: String,
) {

    fun toRequest() = UpdateProductRequest(
        deskripsi = deskripsi,
        hargaNormal = hargaNormal,
        hargaPromo = hargaPromo,
        isHargaPromo = isHargaPromo,
        kategoriId = kategoriId,
        namaProduk = namaProduk,
        qtyStock = qtyStock,
        subKategoriId = subKategoriId,
        beratGram = beratGram,
        produkId = produkId,
    )

    fun toDirectUpdateRequest() = DirectUpdateProductRequest(
        hargaNormal = hargaNormal,
        hargaPromo = hargaPromo,
        isHargaPromo = isHargaPromo,
        qtyStock = qtyStock,
        beratGram = beratGram,
        produkId = produkId,
    )
}
