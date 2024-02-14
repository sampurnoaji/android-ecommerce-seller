package id.io.olebsai.domain.repository

import id.io.olebsai.domain.model.product.Category
import id.io.olebsai.domain.model.product.CreateProductParams
import id.io.olebsai.domain.model.product.Product
import id.io.olebsai.domain.model.product.SubCategory
import id.io.olebsai.domain.model.product.UpdateProductParams
import id.io.olebsai.util.LoadState
import okhttp3.MultipartBody.Part

interface ProductRepository {
    suspend fun getCategoryList(): LoadState<List<Category>>
    suspend fun getSubCategoryList(kategoriId: String): LoadState<List<SubCategory>>

    suspend fun uploadImage(productId: String, part: Part): LoadState<String>
    suspend fun registerImage(
        url: String,
        productId: String,
        filename: String,
        extension: String,
    ): LoadState<Any>

    suspend fun deleteImage(pictureId: String): LoadState<Any>
    suspend fun createProduct(createProductParams: CreateProductParams): LoadState<String>
    suspend fun updateProduct(updateProductParams: UpdateProductParams): LoadState<String>
    suspend fun directUpdateProduct(updateProductParams: UpdateProductParams): LoadState<String>
    suspend fun getAllProductApproval(tokoId: String): LoadState<List<Product>>
    suspend fun getProductApprovalDetail(approvalId: String): LoadState<Product>
    suspend fun getAllProduct(tokoId: String): LoadState<List<Product>>
    suspend fun getProductDetail(productId: String): LoadState<Product>
    suspend fun deleteProduct(productId: String): LoadState<Any>
}