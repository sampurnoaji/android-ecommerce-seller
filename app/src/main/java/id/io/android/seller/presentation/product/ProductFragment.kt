package id.io.android.seller.presentation.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.seller.R
import id.io.android.seller.core.BaseFragment
import id.io.android.seller.databinding.FragmentProductBinding
import id.io.android.seller.util.viewBinding


@AndroidEntryPoint
class ProductFragment :
    BaseFragment<FragmentProductBinding, ProductViewModel>(R.layout.fragment_product) {

    override val binding: FragmentProductBinding by viewBinding(FragmentProductBinding::bind)
    override val vm: ProductViewModel by viewModels()

    private val productListAdapter by lazy { ProductListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductsList()
        observeProducts()
    }

    private fun setupProductsList() {
        binding.rvProducts.adapter = productListAdapter
    }

    private fun observeProducts() {
        vm.products.observe(viewLifecycleOwner) {
            productListAdapter.submitList(it)
        }
    }
}