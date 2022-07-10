package id.io.android.olebsai.domain.repository

import id.io.android.olebsai.domain.model.shop.ShopDetail
import id.io.android.olebsai.util.LoadState

interface ShopRepository {
    suspend fun getShopDetail(): LoadState<ShopDetail>
}