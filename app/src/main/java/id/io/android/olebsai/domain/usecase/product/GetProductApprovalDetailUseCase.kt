package id.io.android.olebsai.domain.usecase.product

import id.io.android.olebsai.core.UseCase
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.util.LoadState
import javax.inject.Inject

class GetProductApprovalDetailUseCase @Inject constructor(
    private val repository: ProductRepository,
) : UseCase<String, LoadState<Product>> {

    override suspend fun invoke(params: String): LoadState<Product> {
        return repository.getProductApprovalDetail(params)
    }
}