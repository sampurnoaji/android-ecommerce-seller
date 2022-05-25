package id.io.android.seller.presentation.product.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.seller.domain.model.product.Product
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor() : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private var query = ""

    init {
        _products.value = populateProducts()
    }

    fun onQuerySearchChanged(query: String) {
        this.query = query
        _products.value = populateProducts().searchProducts()
    }

    private fun List<Product>.searchProducts(): List<Product> = filter {
        it.name.lowercase(Locale.getDefault()).contains(query)
    }

    private fun populateProducts(): List<Product> = listOf(
        Product(
            id = 1,
            name = "Baju",
            price = 12000,
            stock = 14,
            imageUrl = ""
        ),
        Product(
            id = 1,
            name = "Penggaris",
            price = 2000,
            stock = 124,
            imageUrl = ""
        ),
        Product(
            id = 1,
            name = "Penggaris",
            price = 122000,
            stock = 14,
            imageUrl = ""
        ),
        Product(
            id = 1,
            name = "Penggaris",
            price = 120100,
            stock = 143,
            imageUrl = ""
        ),
        Product(
            id = 1,
            name = "Penggaris",
            price = 12000,
            stock = 14,
            imageUrl = ""
        ),
        Product(
            id = 1,
            name = "Penggaris",
            price = 2000,
            stock = 124,
            imageUrl = ""
        ),
        Product(
            id = 1,
            name = "Penggaris",
            price = 122000,
            stock = 14,
            imageUrl = ""
        ),
        Product(
            id = 1,
            name = "Penggaris",
            price = 120100,
            stock = 143,
            imageUrl = ""
        ),
    )
}