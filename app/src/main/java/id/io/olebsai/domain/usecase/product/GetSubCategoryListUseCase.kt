package id.io.olebsai.domain.usecase.product

import id.io.olebsai.core.UseCase
import id.io.olebsai.domain.model.product.SubCategory
import id.io.olebsai.domain.repository.ProductRepository
import id.io.olebsai.util.LoadState
import javax.inject.Inject

class GetSubCategoryListUseCase @Inject constructor(private val repository: ProductRepository) :
    UseCase<String, LoadState<List<SubCategory>>> {

    override suspend fun invoke(params: String): LoadState<List<SubCategory>> {
        return repository.getSubCategoryList(params)
    }
}