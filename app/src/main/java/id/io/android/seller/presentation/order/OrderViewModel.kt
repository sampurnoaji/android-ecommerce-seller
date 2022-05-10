package id.io.android.seller.presentation.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.seller.domain.model.order.Order
import id.io.android.seller.domain.model.product.Product
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val FILTER_ALL_INDEX = 0
        private const val FILTER_NEW_INDEX = 1
        private const val FILTER_ONGOING_INDEX = 2
        private const val FILTER_FINISHED_INDEX = 3
        private const val FILTER_COMPLAINED_INDEX = 4
    }

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>>
        get() = _orders

    init {
        _orders.value = data()
    }

    fun filterOrders(index: Int) {
        val status: Order.Status? = when (index) {
            FILTER_NEW_INDEX -> Order.Status.NEW
            FILTER_ONGOING_INDEX -> Order.Status.ONGOING
            FILTER_FINISHED_INDEX -> Order.Status.FINISHED
            FILTER_COMPLAINED_INDEX -> Order.Status.COMPLAINED
            else -> null
        }
        _orders.value =
            if (status == null) data()
            else data().filter { it.status == status }
    }

    private fun data() =
        listOf(
            Order(
                id = "#ID12345",
                status = Order.Status.NEW,
                customerName = "Rendi Uhuy",
                date = "22 Mei 2022",
                total = 3005007,
                products = mapOf(
                    2 to Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = ""
                    ),
                    1 to Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = ""
                    ),
                )
            ),
            Order(
                id = "#ID12345",
                status = Order.Status.ONGOING,
                customerName = "Noge Ahay",
                date = "22 Mei 2022",
                total = 300500,
                products = mapOf(
                    2 to Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = ""
                    ),
                    1 to Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = ""
                    ),
                )
            ),
            Order(
                id = "#ID12345",
                status = Order.Status.ONGOING,
                customerName = "Joko",
                date = "22 Mei 2022",
                total = 300500,
                products = mapOf(
                    2 to Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = ""
                    ),
                    1 to Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = ""
                    ),
                )
            ),
            Order(
                id = "#ID12345",
                status = Order.Status.FINISHED,
                customerName = "Bach Coy",
                date = "22 Mei 2022",
                total = 300500,
                products = mapOf(
                    2 to Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = ""
                    ),
                    1 to Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = ""
                    ),
                )
            ),
            Order(
                id = "#ID12345",
                status = Order.Status.COMPLAINED,
                customerName = "Cihuy",
                date = "22 Mei 2022",
                total = 300500,
                products = mapOf(
                    2 to Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = ""
                    ),
                    1 to Product(
                        id = 1,
                        name = "Penggaris",
                        price = 12000,
                        stock = 14,
                        imageUrl = ""
                    ),
                )
            )
        )
}