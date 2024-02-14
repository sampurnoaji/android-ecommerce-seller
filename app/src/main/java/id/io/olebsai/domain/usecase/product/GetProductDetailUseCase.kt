package id.io.olebsai.domain.usecase.product

import id.io.olebsai.core.UseCase
import id.io.olebsai.domain.model.product.Product
import id.io.olebsai.domain.repository.ProductRepository
import id.io.olebsai.util.LoadState
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: ProductRepository,
) : UseCase<String, LoadState<Product>> {

    override suspend fun invoke(params: String): LoadState<Product> {
        return repository.getProductDetail(params)
    }
}