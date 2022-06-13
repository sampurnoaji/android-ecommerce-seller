package id.io.android.olebsai.data.source.remote.shop

import id.io.android.olebsai.data.model.request.shop.EditShopRequest
import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.shop.CouriersResponse
import id.io.android.olebsai.data.model.response.shop.ShopDetailResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ShopService {

    @GET("/v1/toko/get-by-user")
    suspend fun getShop(): BaseResponse<ShopDetailResponse>

    @POST("/v1/toko/update")
    suspend fun editShop(@Body request: EditShopRequest): BaseResponse<String>

    @GET("v1/master-kurir/get-all")
    suspend fun getCouriers(): BaseResponse<CouriersResponse>
}