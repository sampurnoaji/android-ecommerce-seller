package id.io.android.olebsai.data.source.remote.shop

import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.shop.ShopDetailResponse
import retrofit2.http.GET

interface ShopService {

    @GET("/v1/toko/get-by-user")
    suspend fun getShop() : BaseResponse<ShopDetailResponse>

    @GET("/v1/toko/update")
    suspend fun updateShop()
}