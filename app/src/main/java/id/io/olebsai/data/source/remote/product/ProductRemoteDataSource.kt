package id.io.olebsai.data.source.remote.product

import id.io.olebsai.data.model.request.product.CreateProductRequest
import id.io.olebsai.data.model.request.product.DeleteImageRequest
import id.io.olebsai.data.model.request.product.DeleteProductRequest
import id.io.olebsai.data.model.request.product.DirectUpdateProductRequest
import id.io.olebsai.data.model.request.product.RegisterImageRequest
import id.io.olebsai.data.model.request.product.UpdateProductRequest
import id.io.olebsai.data.model.response.product.CategoryListResponse
import id.io.olebsai.data.model.response.product.ProductResponse
import id.io.olebsai.data.model.response.product.SubCategoryListResponse
import id.io.olebsai.util.remote.ResponseHelper
import javax.inject.Inject
import okhttp3.MultipartBody.Part

class ProductRemoteDataSource @Inject constructor(private val api: ProductService) :
    ResponseHelper() {

    suspend fun getCategoryList(): CategoryListResponse {
        return call { api.getCategoryList().data }
    }

    suspend fun getSubCategoryList(kategoriId: String): SubCategoryListResponse {
        return call { api.getSubCategoryList(kategoriId).data }
    }

    suspend fun uploadImage(productId: String, part: Part): String {
        return call {
            api.uploadImage(
                url = ProductService.IMAGE_UPLOAD_URL + "/$productId",
                body = part
            ).data
        }
    }

    suspend fun registerImage(
        url: String,
        productId: String,
        filename: String,
        extension: String,
    ): Any {
        return call {
            api.registerImage(
                RegisterImageRequest(
                    extension = extension,
                    url = url,
                    namaFile = filename,
                    parentId = productId,
                    parentType = "produk",
                )
            )
        }
    }

    suspend fun deleteImage(pictureId: String): Any {
        return call { api.deleteImage(DeleteImageRequest(pictureId)) }
    }

    suspend fun createProduct(request: CreateProductRequest): String {
        return call { api.createProduct(request).message.orEmpty() }
    }

    suspend fun updateProduct(request: UpdateProductRequest): String {
        return call { api.updateProduct(request).message.orEmpty() }
    }

    suspend fun directUpdateProduct(request: DirectUpdateProductRequest): String {
        return call { api.directUpdateProduct(request).message.orEmpty() }
    }

    suspend fun getAllProductApproval(tokoId: String): List<ProductResponse> {
        return call { api.getAllProductApproval(tokoId).data }
    }

    suspend fun getProductApprovalDetail(approvalId: String): ProductResponse {
        return call { api.getProductApprovalDetail(approvalId).data }
    }

    suspend fun getAllProduct(tokoId: String): List<ProductResponse> {
        return call { api.getAllProduct(tokoId).data }
    }

    suspend fun getProductDetail(productId: String): ProductResponse {
        return call { api.getProductDetail(productId).data }
    }

    suspend fun deleteProduct(productId: String): Any {
        return call { api.deleteProduct(DeleteProductRequest(productId)) }
    }
}