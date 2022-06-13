package id.io.android.olebsai.presentation.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.domain.model.product.Product
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val FILTER_ALL_INDEX = 0
        private const val FILTER_NEW_INDEX = 1
        private const val FILTER_ONGOING_INDEX = 2
        private const val FILTER_FINISHED_INDEX = 3
        private const val FILTER_COMPLAINED_INDEX = 4
    }

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>>
        get() = _orders

    private var checkedChipIndex = FILTER_ALL_INDEX
    private var query = ""

    init {
        _orders.value = data()
    }

    fun onCheckedChipChanged(index: Int) {
        checkedChipIndex = index
        filterOrders()
    }

    fun onQuerySearchChanged(query: String) {
        this.query = query
        filterOrders()
    }

    private fun filterOrders() {
        val status: Order.Status? = when (checkedChipIndex) {
            FILTER_NEW_INDEX -> Order.Status.NEW
            FILTER_ONGOING_INDEX -> Order.Status.ONGOING
            FILTER_FINISHED_INDEX -> Order.Status.FINISHED
            FILTER_COMPLAINED_INDEX -> Order.Status.COMPLAINED
            else -> null
        }
        _orders.value =
            (if (status == null) data()
            else data().filter { it.status == status }).searchOrders()
    }

    private fun List<Order>.searchOrders(): List<Order> = filter {
        it.customerName.lowercase(Locale.getDefault()).contains(query)
                || it.date.lowercase(Locale.getDefault()).contains(query)
    }

    private fun data() =
        listOf(
            Order(
                id = "#ID12345",
                status = Order.Status.NEW,
                customerName = "Rendi Uhuy",
                date = "22 Mei 2022",
                total = 3005007,
                products = listOf(
                    Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                    Product(
                        id = 2,
                        name = "Celana",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                    Product(
                        id = 3,
                        name = "Laptop",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                    Product(
                        id = 3,
                        name = "Laptop",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                    Product(
                        id = 3,
                        name = "Laptop",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                )
            ),
            Order(
                id = "#ID12345",
                status = Order.Status.ONGOING,
                customerName = "Noge Ahay",
                date = "22 Mei 2022",
                total = 300500,
                products = listOf(
                    Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                    Product(
                        id = 2,
                        name = "Celana",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                    Product(
                        id = 3,
                        name = "Laptop",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                )
            ),
            Order(
                id = "#ID12345",
                status = Order.Status.ONGOING,
                customerName = "Joko",
                date = "22 Mei 2022",
                total = 300500,
                products = listOf(
                    Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                    Product(
                        id = 2,
                        name = "Celana",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                    Product(
                        id = 3,
                        name = "Laptop",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                )
            ),
            Order(
                id = "#ID12345",
                status = Order.Status.FINISHED,
                customerName = "Bach Coy",
                date = "22 Mei 2022",
                total = 300500,
                products = listOf(
                    Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                    Product(
                        id = 2,
                        name = "Celana",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                    Product(
                        id = 3,
                        name = "Laptop",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                    Product(
                        id = 3,
                        name = "Laptop",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                )
            ),
            Order(
                id = "#ID12345",
                status = Order.Status.COMPLAINED,
                customerName = "Cihuy",
                date = "22 Mei 2022",
                total = 300500,
                products = listOf(
                    Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                    Product(
                        id = 2,
                        name = "Celana",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                    Product(
                        id = 3,
                        name = "Laptop",
                        price = 12000,
                        stock = 14,
                        imageUrl = "",
                        qty = 4,
                    ),
                )
            )
        )
}