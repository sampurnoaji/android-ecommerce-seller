package id.io.olebsai.domain.usecase.shop

import id.io.olebsai.core.NoParamsUseCase
import id.io.olebsai.domain.model.shop.ShopDetail
import id.io.olebsai.domain.repository.ShopRepository
import id.io.olebsai.util.LoadState
import javax.inject.Inject

class GetShopDetailUseCase @Inject constructor(private val repository: ShopRepository) :
    NoParamsUseCase<LoadState<ShopDetail>> {

    override suspend fun invoke(): LoadState<ShopDetail> {
        return repository.getShopDetail()
    }
}