package id.io.android.olebsai.domain.usecase.shop

import javax.inject.Inject

data class ShopUseCases @Inject constructor(
    val getShopDetailUseCase: GetShopDetailUseCase,
    val editShopUseCase: EditShopUseCase,

    val saveShopUseCase: SaveShopUseCase,
    val getShopCachedUseCase: GetShopCachedUseCase,
)