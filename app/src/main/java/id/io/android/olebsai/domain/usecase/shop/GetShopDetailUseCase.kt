package id.io.android.olebsai.domain.usecase.shop

import id.io.android.olebsai.core.NoParamsUseCase
import id.io.android.olebsai.domain.model.shop.ShopDetail
import id.io.android.olebsai.domain.repository.ShopRepository
import id.io.android.olebsai.util.LoadState
import javax.inject.Inject

class GetShopDetailUseCase @Inject constructor(private val repository: ShopRepository) :
    NoParamsUseCase<LoadState<ShopDetail>> {

    override suspend fun invoke(): LoadState<ShopDetail> {
        return repository.getShopDetail()
    }
}