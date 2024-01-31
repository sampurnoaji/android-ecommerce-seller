package id.io.android.olebsai.domain.repository

import id.io.android.olebsai.data.source.remote.order.OrderPagingSource
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.domain.model.order.OrderDetail
import id.io.android.olebsai.util.LoadState

interface OrderRepository {
    suspend fun getActiveOrders(): LoadState<List<Order>>
    fun getOrderPagingSource(): OrderPagingSource
    suspend fun getOrderDetail(headerId: String): LoadState<OrderDetail>
    suspend fun deliverOrder(headerId: String, nomorResi: String): LoadState<String>
}