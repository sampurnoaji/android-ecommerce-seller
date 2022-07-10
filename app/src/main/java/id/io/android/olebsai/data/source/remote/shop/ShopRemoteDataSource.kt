package id.io.android.olebsai.data.source.remote.shop

import id.io.android.olebsai.data.model.response.shop.ShopDetailResponse
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class ShopRemoteDataSource @Inject constructor(private val api: ShopService) : ResponseHelper() {

    suspend fun getShopDetail(): ShopDetailResponse {
        return call { api.getShop().data!! }
    }
}