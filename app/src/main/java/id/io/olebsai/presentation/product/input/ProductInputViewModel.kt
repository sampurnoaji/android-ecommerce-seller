package id.io.olebsai.presentation.product.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.olebsai.domain.model.product.Category
import id.io.olebsai.domain.model.product.CreateProductParams
import id.io.olebsai.domain.model.product.Product
import id.io.olebsai.domain.model.product.SubCategory
import id.io.olebsai.domain.model.product.UpdateProductParams
import id.io.olebsai.domain.repository.ProductRepository
import id.io.olebsai.domain.usecase.product.ProductUseCases
import id.io.olebsai.util.LoadState
import id.io.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProductInputViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val productUseCases: ProductUseCases,
) : ViewModel() {

    private val _productDetailResult = SingleLiveEvent<LoadState<Product>>()
    val productDetailResult: LiveData<LoadState<Product>>
        get() = _productDetailResult

    private val _categoryListResult = SingleLiveEvent<LoadState<List<Category>>>()
    val categoryListResult: LiveData<LoadState<List<Category>>>
        get() = _categoryListResult

    private val _subCategoryListResult = SingleLiveEvent<LoadState<List<SubCategory>>>()
    val subCategoryListResult: LiveData<LoadState<List<SubCategory>>>
        get() = _subCategoryListResult

    private val _createProductResult = SingleLiveEvent<LoadState<String>>()
    val createProductResult: LiveData<LoadState<String>>
        get() = _createProductResult

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

    fun getCategoryList() {
        _categoryListResult.value = LoadState.Loading
        viewModelScope.launch {
            _categoryListResult.value = productUseCases.getCategoryListUseCase()
        }
    }

    fun getSubCategoryList(kategoryId: String) {
        _subCategoryListResult.value = LoadState.Loading
        viewModelScope.launch {
            _subCategoryListResult.value = productUseCases.getSubCategoryListUseCase(kategoryId)
        }
    }

    fun createProduct(createProductParams: CreateProductParams) {
        _createProductResult.value = LoadState.Loading
        viewModelScope.launch {
            try {
                _createProductResult.value = productRepository.createProduct(createProductParams)
            } catch (e: Exception) {
                _createProductResult.value = LoadState.Error(message = e.message)
            }
        }
    }

    fun updateProduct(updateProductParams: UpdateProductParams) {
        _createProductResult.value = LoadState.Loading
        viewModelScope.launch {
            try {
                _createProductResult.value = productRepository.updateProduct(updateProductParams)
            } catch (e: Exception) {
                _createProductResult.value = LoadState.Error(message = e.message)
            }
        }
    }

    fun directUpdateProduct(updateProductParams: UpdateProductParams) {
        _createProductResult.value = LoadState.Loading
        viewModelScope.launch {
            try {
                _createProductResult.value = productRepository.directUpdateProduct(updateProductParams)
            } catch (e: Exception) {
                _createProductResult.value = LoadState.Error(message = e.message)
            }
        }
    }
}