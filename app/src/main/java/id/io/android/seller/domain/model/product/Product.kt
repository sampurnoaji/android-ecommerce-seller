package id.io.android.seller.domain.model.product

data class Product(
    val id: Int,
    val name: String,
    val price: Long,
    val stock: Int,
    val imageUrl: String,
    val desc: String = ""
)
