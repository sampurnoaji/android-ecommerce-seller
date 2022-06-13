package id.io.android.olebsai.domain.repository

import id.io.android.olebsai.domain.model.shop.Courier
import id.io.android.olebsai.domain.model.shop.EditShopParams
import id.io.android.olebsai.domain.model.shop.ShopDetail
import id.io.android.olebsai.util.LoadState

interface ShopRepository {
    suspend fun getShopDetail(): LoadState<ShopDetail>
    suspend fun editShop(editShopParams: EditShopParams): LoadState<String>
    suspend fun getCouriers(): LoadState<List<Courier>>
    fun saveShop(shopDetail: ShopDetail)
    fun getShopCached(): ShopDetail?
}