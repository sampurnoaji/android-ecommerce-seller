package id.io.android.seller.presentation.order

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.seller.R
import id.io.android.seller.core.BaseFragment
import id.io.android.seller.databinding.FragmentOrderBinding
import id.io.android.seller.util.viewBinding

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding, OrderViewModel>(R.layout.fragment_order) {

    override val binding: FragmentOrderBinding by viewBinding(FragmentOrderBinding::bind)
    override val vm: OrderViewModel by viewModels()

    private val orderListAdapter by lazy { OrderListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOrderList()
        setupActionView()

        observeOrders()
    }

    private fun setupActionView() {
        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.isRefreshing = false
        }

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            requireActivity().currentFocus?.clearFocus()
            group.children.forEachIndexed { index, child ->
                if (child.id == checkedId) {
                    vm.onCheckedChipChanged(index)
                }
                return@forEachIndexed
            }
        }

        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            vm.onQuerySearchChanged(text.toString())
        }
    }

    private fun setupOrderList() {
        binding.rvOrders.adapter = orderListAdapter
    }

    private fun observeOrders() {
        vm.orders.observe(viewLifecycleOwner) {
            orderListAdapter.submitList(it)
        }
    }
}