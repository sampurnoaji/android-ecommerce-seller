package id.io.olebsai.data.repository

import id.io.olebsai.data.source.local.ShopLocalDataSource
import id.io.olebsai.data.source.remote.shop.ShopRemoteDataSource
import id.io.olebsai.domain.model.shop.Courier
import id.io.olebsai.domain.model.shop.EditShopParams
import id.io.olebsai.domain.model.shop.ShopDetail
import id.io.olebsai.domain.repository.ShopRepository
import id.io.olebsai.util.LoadState
import id.io.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val remoteDataSource: ShopRemoteDataSource,
    private val localDataSource: ShopLocalDataSource,
) : ResponseHelper(), ShopRepository {

    override suspend fun getShopDetail(): LoadState<ShopDetail> {
        return map { remoteDataSource.getShopDetail().toDomain() }
    }

    override suspend fun editShop(editShopParams: EditShopParams): LoadState<String> {
        return map { remoteDataSource.editShop(editShopParams.toRequest()) }
    }

    override suspend fun getCouriers(): LoadState<List<Courier>> {
        return map { remoteDataSource.getCouriers().map { it.toDomain() } }
    }

    override fun saveShop(shopDetail: ShopDetail) {
        localDataSource.saveShop(shopDetail)
    }

    override fun getShopCached(): ShopDetail? = localDataSource.getShop()
}