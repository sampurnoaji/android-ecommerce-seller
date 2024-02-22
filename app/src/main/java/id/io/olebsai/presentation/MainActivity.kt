package id.io.olebsai.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import id.io.olebsai.R
import id.io.olebsai.core.BaseActivity
import id.io.olebsai.databinding.ActivityMainBinding
import id.io.olebsai.presentation.order.history.OrderFragment
import id.io.olebsai.presentation.product.list.ProductFragment
import id.io.olebsai.presentation.shop.ShopViewModel
import id.io.olebsai.presentation.user.home.HomeFragment
import id.io.olebsai.presentation.user.landing.LandingActivity
import id.io.olebsai.presentation.user.login.LoginActivity
import id.io.olebsai.util.viewBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    override val vm: MainViewModel by viewModels()
    private val shopViewModel: ShopViewModel by viewModels()

    private val homeFragment by lazy { HomeFragment() }
    private val orderFragment by lazy { OrderFragment() }
    private val productFragment by lazy { ProductFragment() }

    private var currentFragment: Fragment = homeFragment

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleRedirectPageNavigation()
        setupFragments()

        shopViewModel.getShopDetail()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (currentFragment != homeFragment)
                    binding.bottomNavigation.selectedItemId = R.id.menuHome
                else finish()
            }
        })
    }

    private fun setupFragments() {
        showFragment(currentFragment)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> {
                    showFragment(homeFragment)
                    true
                }

                R.id.menuOrder -> {
                    showFragment(orderFragment)
                    true
                }

                R.id.menuProduct -> {
                    showFragment(productFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        with(supportFragmentManager) {
            if (!fragment.isAdded) {
                commit { add(R.id.container, fragment) }
            }
            commit {
                hide(currentFragment)
                show(fragment)
            }
        }
        currentFragment = fragment
    }

    private fun handleRedirectPageNavigation() {
        if (vm.isFirstLaunchApp()) {
            startActivity(Intent(this, LandingActivity::class.java))
            finish()
            return
        }

        if (!vm.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    internal fun navigateToHome() {
        binding.bottomNavigation.selectedItemId = R.id.menuHome
    }
}