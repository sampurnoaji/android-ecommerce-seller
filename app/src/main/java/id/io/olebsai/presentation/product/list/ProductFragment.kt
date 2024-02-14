package id.io.olebsai.presentation.product.list

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import id.io.olebsai.R
import id.io.olebsai.R.string
import id.io.olebsai.core.BaseFragment
import id.io.olebsai.databinding.FragmentProductBinding
import id.io.olebsai.domain.model.product.Product
import id.io.olebsai.presentation.product.detail.ProductDetailActivity
import id.io.olebsai.presentation.product.input.ProductInputActivity
import id.io.olebsai.presentation.shop.ShopViewModel
import id.io.olebsai.util.viewBinding


@AndroidEntryPoint
class ProductFragment :
    BaseFragment<FragmentProductBinding, ProductViewModel>(R.layout.fragment_product) {

    override val binding: FragmentProductBinding by viewBinding(FragmentProductBinding::bind)
    override val vm: ProductViewModel by viewModels()
    private val shopViewModel: ShopViewModel by viewModels()

    private val productListAdapter by lazy {
        ProductListAdapter(
            object : ProductListAdapter.Listener {
                override fun onItemClicked(product: Product) {
                    ProductDetailActivity.start(
                        requireContext(), launcher,
                        if (product.status == Product.ApprovalStatus.APPROVE) product.produkId
                        else product.approvalId,
                        status = product.status.status
                    )
                }

                override fun onSeeApprovalDetailClicked(product: Product) {
                    ProductDetailActivity.start(
                        requireContext(),
                        launcher,
                        product.produkId,
                        Product.ApprovalStatus.EDIT.status,
                    )
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getAllProduct()
        setupView()
        setupActionView()
        setupProductsList()
        observeProducts()
    }

    private fun setupView() {
        with(binding.toolbar) {
            imgBack.isGone = true
            tvTitle.text = getString(string.product_list)
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                vm.getAllProduct()
            }
        }

    private fun setupActionView() {
        binding.refreshLayout.setOnRefreshListener {
            vm.getAllProduct()
        }

        binding.btnAdd.setOnClickListener {
            if (shopViewModel.getShopCached()?.isApproved() == true)
                ProductInputActivity.start(requireContext(), launcher)
            else showInfoDialog(getString(string.shop_status_registration_info))
        }
    }

    private fun setupProductsList() {
        with(binding.rvProducts) {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = productListAdapter
        }
    }

    private fun observeProducts() {
        vm.productsResult.observe(
            embedLoading = false,
            onLoading = {
                binding.refreshLayout.isRefreshing = true
                binding.tvEmptyData.isGone = true
            },
            onSuccess = {
                binding.refreshLayout.isRefreshing = false
                productListAdapter.submitList(it)
                binding.rvProducts.isVisible = !it.isNullOrEmpty()
                binding.tvEmptyData.isVisible = it.isNullOrEmpty()
            },
            onError = {
                binding.refreshLayout.isRefreshing = false
                binding.rvProducts.isGone = true
                binding.tvEmptyData.isVisible = true
            },
        )
    }
}