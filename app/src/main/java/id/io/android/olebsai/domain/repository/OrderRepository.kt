package id.io.android.olebsai.domain.repository

import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.domain.model.order.OrderDetail
import id.io.android.olebsai.util.LoadState

interface OrderRepository {
    suspend fun getActiveOrders(): LoadState<List<Order>>
    suspend fun getDoneOrders(): LoadState<List<Order>>
    suspend fun getOrderDetail(headerId: String): LoadState<OrderDetail>
    suspend fun deliverOrder(headerId: String, nomorResi: String): LoadState<String>
}