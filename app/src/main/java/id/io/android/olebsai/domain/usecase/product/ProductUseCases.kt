package id.io.android.olebsai.domain.usecase.product

import javax.inject.Inject

data class ProductUseCases @Inject constructor(
    val getCategoryListUseCase: GetCategoryListUseCase,
    val getSubCategoryListUseCase: GetSubCategoryListUseCase,
    val createProductUseCase: CreateProductUseCase,
    val getAllProductApprovalUseCase: GetAllProductApprovalUseCase,
    val getProductApprovalDetailUseCase: GetProductApprovalDetailUseCase,
    val getAllProductUseCase: GetAllProductUseCase,
    val getProductDetailUseCase: GetProductDetailUseCase,
)