package id.io.olebsai.domain.model.product

data class OrderedProduct(
    val catatan: String,
    val hargaSatuan: Long,
    val hargaTotal: Long,
    val namaProduk: String,
    val pesananHeaderId: String,
    val pesananId: String,
    val qty: Int
)
