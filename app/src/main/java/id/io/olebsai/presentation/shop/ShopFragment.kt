package id.io.olebsai.presentation.shop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.olebsai.R
import id.io.olebsai.core.BaseFragment
import id.io.olebsai.databinding.FragmentShopBinding
import id.io.olebsai.util.viewBinding

@AndroidEntryPoint
class ShopFragment : BaseFragment<FragmentShopBinding, ShopViewModel>(R.layout.fragment_shop) {

    override val binding: FragmentShopBinding by viewBinding(FragmentShopBinding::bind)
    override val vm: ShopViewModel by viewModels()

    private val shopListAdapter by lazy { ShopListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupShopList()

        observeShops()
    }

    private fun setupShopList() {
        binding.rvShops.adapter = shopListAdapter
    }

    private fun observeShops() {
        vm.shopDetailResult.observe(viewLifecycleOwner) {

        }
    }
}