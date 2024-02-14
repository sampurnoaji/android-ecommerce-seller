package id.io.olebsai.presentation.product.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.olebsai.domain.model.product.Product
import id.io.olebsai.domain.usecase.product.ProductUseCases
import id.io.olebsai.util.LoadState
import id.io.olebsai.util.SingleLiveEvent
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ProductViewModel @Inject constructor(private val productUseCases: ProductUseCases) :
    ViewModel() {

    private val _productsResult = SingleLiveEvent<LoadState<List<Product>>>()
    val productsResult: LiveData<LoadState<List<Product>>>
        get() = _productsResult

    private val products = mutableListOf<Product>()

    private var query = ""

    fun getAllProduct() {
        _productsResult.value = LoadState.Loading
        viewModelScope.launch {
            val pendingProducts =
                withContext(Dispatchers.IO) { productUseCases.getAllProductApprovalUseCase() }
            val approvedProduct =
                withContext(Dispatchers.IO) { productUseCases.getAllProductUseCase() }

            if (pendingProducts is LoadState.Success && approvedProduct is LoadState.Success) {
                val results = (approvedProduct.data + pendingProducts.data).toMutableList()

                val duplicateProductIds =
                    results.groupingBy { it.produkId }.eachCount().filter { it.value > 1 }

                duplicateProductIds.forEach { id ->
                    val first = results.first { it.produkId == id.key }
                    val last = results.last { it.produkId == id.key }

                    results.remove(first)
                    results.remove(last)
                    results.add(0, first.copy(isUpdatingProgress = true))
                }

                products.clear()
                products.addAll(results)
                _productsResult.value = LoadState.Success(results)
            } else LoadState.Success(emptyList<Product>())
        }
    }

    fun onQuerySearchChanged(query: String) {
        this.query = query
        _productsResult.value = LoadState.Success(products.searchProducts())
    }

    private fun List<Product>.searchProducts(): List<Product> = filter {
        it.namaProduk.lowercase(Locale.getDefault()).contains(query)
    }
}