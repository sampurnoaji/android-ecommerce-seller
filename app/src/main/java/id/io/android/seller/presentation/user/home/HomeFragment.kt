package id.io.android.seller.presentation.user.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.seller.R
import id.io.android.seller.core.BaseFragment
import id.io.android.seller.databinding.FragmentHomeBinding
import id.io.android.seller.util.viewBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    override val vm: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupDashBoardCard()
    }

    private fun setupToolbar() {
        binding.toolbar.title = "Bejo Shop"
    }

    private fun setupDashBoardCard() {
        binding.card1.setValue("1")
        binding.card2.setValue("3")
        binding.card3.setValue("5")
    }
}