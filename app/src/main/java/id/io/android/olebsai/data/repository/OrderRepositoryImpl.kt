package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.source.remote.order.OrderRemoteDataSource
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.domain.model.order.OrderDetail
import id.io.android.olebsai.domain.repository.OrderRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val remoteDataSource: OrderRemoteDataSource) :
    OrderRepository, ResponseHelper() {

    override suspend fun getActiveOrders(): LoadState<List<Order>> {
        return map { remoteDataSource.getActiveOrders().map { it.toDomain() } }
    }

    override suspend fun getDoneOrders(): LoadState<List<Order>> {
        return map { remoteDataSource.getDoneOrders().map { it.toDomain() } }
    }

    override suspend fun getOrderDetail(headerId: String): LoadState<OrderDetail> {
        return map { remoteDataSource.getOrderDetail(headerId).toDomain() }
    }

    override suspend fun deliverOrder(headerId: String, nomorResi: String): LoadState<String> {
        return map { remoteDataSource.deliverOrder(headerId, nomorResi) }
    }
}