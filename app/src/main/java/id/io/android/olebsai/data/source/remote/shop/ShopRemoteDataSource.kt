package id.io.android.olebsai.data.source.remote.shop

import id.io.android.olebsai.data.model.request.shop.EditShopRequest
import id.io.android.olebsai.data.model.response.shop.CouriersResponse.CourierResponse
import id.io.android.olebsai.data.model.response.shop.ShopDetailResponse
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class ShopRemoteDataSource @Inject constructor(private val api: ShopService) : ResponseHelper() {

    suspend fun getShopDetail(): ShopDetailResponse {
        return call { api.getShop().data }
    }

    suspend fun editShop(request: EditShopRequest): String {
        return call { api.editShop(request).message.orEmpty() }
    }

    suspend fun getCouriers(): List<CourierResponse> {
        return call { api.getCouriers().data.data }
    }
}