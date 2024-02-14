package id.io.olebsai.domain.usecase.product

import id.io.olebsai.core.NoParamsUseCase
import id.io.olebsai.domain.model.product.Category
import id.io.olebsai.domain.repository.ProductRepository
import id.io.olebsai.util.LoadState
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(private val repository: ProductRepository) :
    NoParamsUseCase<LoadState<List<Category>>> {

    override suspend fun invoke(): LoadState<List<Category>> {
        return repository.getCategoryList()
    }
}