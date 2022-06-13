package id.io.android.olebsai.presentation.product.detail

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.R
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.domain.usecase.product.ProductUseCases
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.SingleLiveEvent
import id.io.android.olebsai.util.isOver2MB
import id.io.android.olebsai.util.toFile
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val _productDetailResult = SingleLiveEvent<LoadState<Product>>()
    val productDetailResult: LiveData<LoadState<Product>>
        get() = _productDetailResult

    private val _uploadImageResult = SingleLiveEvent<LoadState<Any>>()
    val uploadImageResult: LiveData<LoadState<Any>>
        get() = _uploadImageResult

    private val _deleteImageResult = SingleLiveEvent<LoadState<Any>>()
    val deleteImageResult: LiveData<LoadState<Any>>
        get() = _deleteImageResult

    fun getProductApprovalDetail(approvalId: String) {
        _productDetailResult.value = LoadState.Loading
        viewModelScope.launch {
            _productDetailResult.value = productUseCases.getProductApprovalDetailUseCase(approvalId)
        }
    }

    fun getProductDetail(productId: String) {
        _productDetailResult.value = LoadState.Loading
        viewModelScope.launch {
            _productDetailResult.value = productUseCases.getProductDetailUseCase(productId)
        }
    }

    fun uploadImage(context: Context, uri: Uri, productId: String) {
        _uploadImageResult.value = LoadState.Loading
        viewModelScope.launch {
            try {
                uri.toFile(context).let { imageFile ->
                    if (imageFile == null) {
                        _uploadImageResult.value =
                            LoadState.Error(message = context.getString(R.string.error))
                        return@launch
                    }
                    val file = File(imageFile.path)
                    if (file.isOver2MB()) {
                        _uploadImageResult.value =
                            LoadState.Error(message = context.getString(R.string.product_image_over_2mb))
                        return@launch
                    }

                    val fileExtension = context.contentResolver.getType(uri).orEmpty()
                    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                    val imagePart =
                        MultipartBody.Part.createFormData("image", file.name, requestBody)
                    val uploadResult =
                        withContext(Dispatchers.IO) { productRepository.uploadImage(imagePart) }
                    _uploadImageResult.value =
                        if (uploadResult is LoadState.Success)
                            productRepository.registerImage(
                                url = uploadResult.data,
                                productId = productId,
                                filename = file.name,
                                extension = fileExtension,
                            )
                        else uploadResult
                }
            } catch (e: Exception) {
                _uploadImageResult.value = LoadState.Error(message = e.message)
            }
        }
    }

    fun deleteImage(pictureId: String) {
        _deleteImageResult.value = LoadState.Loading
        viewModelScope.launch {
            _deleteImageResult.value = productRepository.deleteImage(pictureId)
        }
    }
}