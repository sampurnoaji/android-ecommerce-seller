package id.io.android.olebsai.domain.usecase.product

import id.io.android.olebsai.core.NoParamsUseCase
import id.io.android.olebsai.domain.model.product.Category
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.util.LoadState
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(private val repository: ProductRepository) :
    NoParamsUseCase<LoadState<List<Category>>> {

    override suspend fun invoke(): LoadState<List<Category>> {
        return repository.getCategoryList()
    }
}