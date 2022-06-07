package id.io.android.olebsai.presentation.product.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentProductBinding
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.presentation.product.detail.ProductDetailActivity
import id.io.android.olebsai.util.viewBinding


@AndroidEntryPoint
class ProductFragment :
    BaseFragment<FragmentProductBinding, ProductViewModel>(R.layout.fragment_product) {

    override val binding: FragmentProductBinding by viewBinding(FragmentProductBinding::bind)
    override val vm: ProductViewModel by viewModels()

    private val productListAdapter by lazy {
        ProductListAdapter(
            object : ProductListAdapter.Listener {
                override fun onItemClicked(product: Product) {
                    val intent = Intent(requireContext(), ProductDetailActivity::class.java)
                    startActivity(intent)
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionView()
        setupProductsList()
        observeProducts()
    }

    private fun setupActionView() {
        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.isRefreshing = false
        }

        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            vm.onQuerySearchChanged(text.toString())
        }
    }

    private fun setupProductsList() {
        with(binding.rvProducts) {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = productListAdapter
        }
    }

    private fun observeProducts() {
        vm.products.observe(viewLifecycleOwner) {
            productListAdapter.submitList(it)
        }
    }
}