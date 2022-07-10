package id.io.android.olebsai.domain.usecase.shop

import javax.inject.Inject

data class ShopUseCases @Inject constructor(
    val getShopDetailUseCase: GetShopDetailUseCase,
)