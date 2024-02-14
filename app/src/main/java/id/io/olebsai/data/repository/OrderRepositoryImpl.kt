package id.io.olebsai.data.repository

import id.io.olebsai.data.source.remote.order.OrderPagingSource
import id.io.olebsai.data.source.remote.order.OrderRemoteDataSource
import id.io.olebsai.domain.model.order.Order
import id.io.olebsai.domain.model.order.OrderDetail
import id.io.olebsai.domain.repository.OrderRepository
import id.io.olebsai.util.LoadState
import id.io.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val remoteDataSource: OrderRemoteDataSource) :
    OrderRepository, ResponseHelper() {

    override suspend fun getActiveOrders(): LoadState<List<Order>> {
        return map { remoteDataSource.getActiveOrders().map { it.toDomain() } }
    }

    override fun getOrderPagingSource(): OrderPagingSource {
        return OrderPagingSource(remoteDataSource)
    }

    override suspend fun getOrderDetail(headerId: String): LoadState<OrderDetail> {
        return map { remoteDataSource.getOrderDetail(headerId).toDomain() }
    }

    override suspend fun deliverOrder(headerId: String, nomorResi: String): LoadState<String> {
        return map { remoteDataSource.deliverOrder(headerId, nomorResi) }
    }
}