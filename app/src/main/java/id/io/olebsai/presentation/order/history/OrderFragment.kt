package id.io.olebsai.presentation.order.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.olebsai.R
import id.io.olebsai.core.BaseFragment
import id.io.olebsai.databinding.FragmentOrderBinding
import id.io.olebsai.presentation.MainActivity
import id.io.olebsai.presentation.shop.ShopViewModel
import id.io.olebsai.util.viewBinding

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding, OrderViewModel>(R.layout.fragment_order) {

    override val binding by viewBinding(FragmentOrderBinding::bind)
    override val vm: OrderViewModel by viewModels()
    private val shopViewModel: ShopViewModel by activityViewModels()

    private val pagerAdapter by lazy { OrderPagerAdapter(childFragmentManager, lifecycle) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopViewModel.getShopCached()?.let { shop ->
            if (shop.isStatusRegistrasi()) {
                showInfoDialog(
                    message = getString(R.string.shop_status_registration_info_action),
                    onCloseDialog = { (activity as MainActivity).navigateToHome() }
                )
                return
            }
            setupView()
        }
    }

    private fun setupView() {
        with(binding) {
            toggleGroup.addOnButtonCheckedListener { _, _, _ ->
                pager.currentItem = if (btnOngoing.isChecked) 0 else 1
            }
        }

        with(binding.pager) {
            adapter = pagerAdapter
        }
    }
}