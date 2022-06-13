package id.io.android.olebsai.data.source.remote.product

import id.io.android.olebsai.data.model.request.product.CreateProductRequest
import id.io.android.olebsai.data.model.request.product.DeleteImageRequest
import id.io.android.olebsai.data.model.request.product.RegisterImageRequest
import id.io.android.olebsai.data.model.request.product.UpdateProductRequest
import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.product.CategoryListResponse
import id.io.android.olebsai.data.model.response.product.ProductResponse
import id.io.android.olebsai.data.model.response.product.SubCategoryListResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ProductService {

    companion object {
        const val IMAGE_UPLOAD_URL = "https://image.olebsai.com/api/upload-image"
    }

    @GET("/v1/master-kategori/get-all")
    suspend fun getCategoryList(): BaseResponse<CategoryListResponse>

    @GET("/v1/master-sub-kategori/get-all")
    suspend fun getSubCategoryList(
        @Query("kategoriId") kategoriId: String,
    ): BaseResponse<SubCategoryListResponse>

    @Multipart
    @POST
    suspend fun uploadImage(
        @Url url: String,
        @Part body: MultipartBody.Part
    ): BaseResponse<String>

    @POST("/v1/picture/create")
    suspend fun registerImage(@Body request: RegisterImageRequest): BaseResponse<Any>

    @POST("/v1/picture/delete")
    suspend fun deleteImage(@Body request: DeleteImageRequest): BaseResponse<Any>

    @POST("/v1/produk/create")
    suspend fun createProduct(@Body request: CreateProductRequest): BaseResponse<Any>

    @POST("/v1/produk/edit")
    suspend fun updateProduct(@Body request: UpdateProductRequest): BaseResponse<Any>

    @GET("/v1/approval-produk/get-by-toko/{tokoId}")
    suspend fun getAllProductApproval(
        @Path("tokoId") tokoId: String
    ): BaseResponse<List<ProductResponse>>

    @GET("/v1/approval-produk/get-by-id/{approvalId}")
    suspend fun getProductApprovalDetail(
        @Path("approvalId") approvalId: String
    ): BaseResponse<ProductResponse>

    @GET("/v1/produk/get-by-toko")
    suspend fun getAllProduct(@Query("tokoId") tokoId: String): BaseResponse<List<ProductResponse>>

    @GET("/v1/produk/get-by-id/{productId}")
    suspend fun getProductDetail(
        @Path("productId") productId: String
    ): BaseResponse<ProductResponse>
}