package id.io.android.olebsai.domain.usecase.shop

import id.io.android.olebsai.domain.model.shop.ShopDetail
import id.io.android.olebsai.domain.repository.ShopRepository
import javax.inject.Inject

class GetShopCachedUseCase @Inject constructor(private val repository: ShopRepository) {
    operator fun invoke(): ShopDetail? = repository.getShopCached()
}