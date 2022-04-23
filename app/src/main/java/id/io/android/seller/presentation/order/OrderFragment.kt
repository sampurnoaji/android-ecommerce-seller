package id.io.android.seller.presentation.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.seller.R
import id.io.android.seller.core.BaseFragment
import id.io.android.seller.databinding.FragmentOrderBinding
import id.io.android.seller.domain.model.order.Order
import id.io.android.seller.domain.model.product.Product
import id.io.android.seller.util.viewBinding

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding, OrderViewModel>(R.layout.fragment_order) {

    override val binding: FragmentOrderBinding by viewBinding(FragmentOrderBinding::bind)
    override val vm: OrderViewModel by viewModels()

    private val orderListAdapter by lazy { OrderListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOrderList()
    }

    private fun setupOrderList() {
        binding.rvOrders.adapter = orderListAdapter
        orderListAdapter.submitList(orders)
    }

    private val orders by lazy {
        listOf(
            Order(
                id = "#ID12345",
                customerName = "Rendi Uhuy",
                date = "22 Mei 2022",
                total = 300500,
                products = mapOf(
                    2 to Product(id = 1, name = "Minyak Wangi"),
                    1 to Product(id = 1, name = "Spring Bed"),
                )
            ),
            Order(
                id = "#ID12345",
                customerName = "Noge Ahay",
                date = "22 Mei 2022",
                total = 300500,
                products = mapOf(
                    2 to Product(id = 1, name = "Bantal"),
                    1 to Product(id = 1, name = "Sarung"),
                )
            ),
            Order(
                id = "#ID12345",
                customerName = "Bach Coy",
                date = "22 Mei 2022",
                total = 300500,
                products = mapOf(
                    2 to Product(id = 1, name = "Yak"),
                    1 to Product(id = 1, name = "NOwindoinfuinriufnir"),
                )
            ),
            Order(
                id = "#ID12345",
                customerName = "Bach Coy",
                date = "22 Mei 2022",
                total = 300500,
                products = mapOf(
                    2 to Product(id = 1, name = "Yak"),
                    1 to Product(id = 1, name = "NOwindoinfuinriufnir"),
                )
            )
        )
    }
}