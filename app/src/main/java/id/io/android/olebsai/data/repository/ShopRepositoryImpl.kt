package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.source.remote.shop.ShopRemoteDataSource
import id.io.android.olebsai.domain.model.shop.ShopDetail
import id.io.android.olebsai.domain.repository.ShopRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(private val remoteDataSource: ShopRemoteDataSource)
    : ResponseHelper(), ShopRepository {

    override suspend fun getShopDetail(): LoadState<ShopDetail> {
        return map { remoteDataSource.getShopDetail().toDomain() }
    }
}