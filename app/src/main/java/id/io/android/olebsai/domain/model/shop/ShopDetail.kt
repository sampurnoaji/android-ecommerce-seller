package id.io.android.olebsai.domain.model.shop

data class ShopDetail(
    val id: String,
    val userId: String,
    val address: String,
    val logo: String,
    val note: String,
    val postalCode: String,
    val city: String,
    val name: String,
    val province: String,
)