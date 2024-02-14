package id.io.olebsai.domain.repository

import id.io.olebsai.data.source.remote.order.OrderPagingSource
import id.io.olebsai.domain.model.order.Order
import id.io.olebsai.domain.model.order.OrderDetail
import id.io.olebsai.util.LoadState

interface OrderRepository {
    suspend fun getActiveOrders(): LoadState<List<Order>>
    fun getOrderPagingSource(): OrderPagingSource
    suspend fun getOrderDetail(headerId: String): LoadState<OrderDetail>
    suspend fun deliverOrder(headerId: String, nomorResi: String): LoadState<String>
}