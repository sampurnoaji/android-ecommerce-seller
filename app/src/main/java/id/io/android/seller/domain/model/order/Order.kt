package id.io.android.seller.domain.model.order

import id.io.android.seller.domain.model.product.Product

data class Order(
    val id: String,
    val customerName: String,
    val date: String,
    val total: Long,
    val products: Map<Int, Product>
)
