package id.io.android.olebsai.data.source.remote.order

import id.io.android.olebsai.data.model.request.order.DeliverOrderRequest
import id.io.android.olebsai.data.model.response.order.OrderDetailResponse
import id.io.android.olebsai.data.model.response.order.OrderResponse
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class OrderRemoteDataSource @Inject constructor(private val service: OrderService) :
    ResponseHelper() {

    suspend fun getActiveOrders(): List<OrderResponse> {
        return call { service.getActiveOrders().data.data }
    }

    suspend fun getDoneOrders(
        page: Int,
        size: Int,
    ): List<OrderResponse> {
        return call {
            service.getDoneOrders(
                page = page,
                size = size,
            ).data.data
        }
    }

    suspend fun getOrderDetail(headerId: String): OrderDetailResponse {
        return call { service.getOrderDetail(headerId).data }
    }

    suspend fun deliverOrder(headerId: String, nomorResi: String): String {
        return call {
            service.deliverOrder(
                DeliverOrderRequest(
                    pesananHeaderId = headerId,
                    nomorResi = nomorResi,
                )
            ).message.orEmpty()
        }
    }
}