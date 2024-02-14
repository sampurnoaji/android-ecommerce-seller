package id.io.olebsai.data.source.remote.order

import id.io.olebsai.data.model.request.order.DeliverOrderRequest
import id.io.olebsai.data.model.response.BaseResponse
import id.io.olebsai.data.model.response.order.ActiveOrderResponse
import id.io.olebsai.data.model.response.order.OrderDetailResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderService {

    @GET("v1/pesanan/get-by-seller")
    suspend fun getActiveOrders(): BaseResponse<ActiveOrderResponse>

    @GET("v1/pesanan/get-histori-pesanan-seller")
    suspend fun getDoneOrders(
        @Query("pageNumber") page: Int,
        @Query("pageSize") size: Int,
    ): BaseResponse<ActiveOrderResponse>

    @GET("/v1/pesanan/get-list-pesanan-by-header/{headerId}")
    suspend fun getOrderDetail(
        @Path("headerId") headerId: String
    ): BaseResponse<OrderDetailResponse>

    @POST("/v1/pesanan/kirim-dari-toko")
    suspend fun deliverOrder(@Body request: DeliverOrderRequest): BaseResponse<Any>
}