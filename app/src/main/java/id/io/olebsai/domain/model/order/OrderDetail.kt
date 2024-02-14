package id.io.olebsai.domain.model.order

import id.io.olebsai.domain.model.product.OrderedProduct

data class OrderDetail(
    val order: Order,
    val orderedProducts: List<OrderedProduct>,
)
