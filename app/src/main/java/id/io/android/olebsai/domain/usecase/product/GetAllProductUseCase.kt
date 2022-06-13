package id.io.android.olebsai.domain.usecase.product

import id.io.android.olebsai.core.NoParamsUseCase
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.domain.repository.ShopRepository
import id.io.android.olebsai.util.LoadState
import javax.inject.Inject

class GetAllProductUseCase @Inject constructor(
    private val repository: ProductRepository,
    private val shopRepository: ShopRepository,
) : NoParamsUseCase<LoadState<List<Product>>> {

    override suspend fun invoke(): LoadState<List<Product>> {
        val shop = shopRepository.getShopCached()
        return repository.getAllProduct(shop?.id.orEmpty())
    }
}