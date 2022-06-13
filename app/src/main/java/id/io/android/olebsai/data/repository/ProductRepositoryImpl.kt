package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.source.remote.product.ProductRemoteDataSource
import id.io.android.olebsai.domain.model.product.Category
import id.io.android.olebsai.domain.model.product.CreateProductParams
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.model.product.SubCategory
import id.io.android.olebsai.domain.model.product.UpdateProductParams
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject
import okhttp3.MultipartBody.Part

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : ResponseHelper(), ProductRepository {

    override suspend fun getCategoryList(): LoadState<List<Category>> {
        return map { remoteDataSource.getCategoryList().data?.map { it.toDomain() } ?: emptyList() }
    }

    override suspend fun getSubCategoryList(kategoriId: String): LoadState<List<SubCategory>> {
        return map {
            remoteDataSource.getSubCategoryList(kategoriId).data?.map { it.toDomain() }
                ?: emptyList()
        }
    }

    override suspend fun createProduct(createProductParams: CreateProductParams): LoadState<Any> {
        return map { remoteDataSource.createProduct(createProductParams.toRequest()) }
    }

    override suspend fun updateProduct(updateProductParams: UpdateProductParams): LoadState<Any> {
        return map { remoteDataSource.updateProduct(updateProductParams.toRequest()) }
    }

    override suspend fun uploadImage(part: Part): LoadState<String> {
        return map { remoteDataSource.uploadImage(part) }
    }

    override suspend fun registerImage(
        url: String,
        productId: String,
        filename: String,
        extension: String,
    ): LoadState<Any> {
        return map {
            remoteDataSource.registerImage(
                url = url,
                productId = productId,
                filename = filename,
                extension = extension,
            )
        }
    }

    override suspend fun deleteImage(pictureId: String): LoadState<Any> {
        return map { remoteDataSource.deleteImage(pictureId) }
    }

    override suspend fun getAllProductApproval(tokoId: String): LoadState<List<Product>> {
        return map { remoteDataSource.getAllProductApproval(tokoId).map { it.toDomain() } }
    }

    override suspend fun getProductApprovalDetail(approvalId: String): LoadState<Product> {
        return map { remoteDataSource.getProductApprovalDetail(approvalId).toDomain() }
    }

    override suspend fun getAllProduct(tokoId: String): LoadState<List<Product>> {
        return map { remoteDataSource.getAllProduct(tokoId).map { it.toDomain() } }
    }

    override suspend fun getProductDetail(productId: String): LoadState<Product> {
        return map { remoteDataSource.getProductDetail(productId).toDomain() }
    }
}