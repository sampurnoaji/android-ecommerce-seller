package id.io.android.olebsai.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityMainBinding
import id.io.android.olebsai.presentation.order.OrderFragment
import id.io.android.olebsai.presentation.product.list.ProductFragment
import id.io.android.olebsai.presentation.user.account.AccountFragment
import id.io.android.olebsai.presentation.user.home.HomeFragment
import id.io.android.olebsai.presentation.user.login.LoginActivity
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    override val vm: MainViewModel by viewModels()

    private val homeFragment by lazy { HomeFragment() }
    private val orderFragment by lazy { OrderFragment() }
    private val productFragment by lazy { ProductFragment() }
    private val accountFragment by lazy { AccountFragment() }
    private var currentFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLoggedInStatus()
        setupFragments()
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
                R.id.menuAccount -> {
                    showFragment(accountFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        if (currentFragment != homeFragment) binding.bottomNavigation.selectedItemId = R.id.menuHome
        else super.onBackPressed()
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

    private fun checkLoggedInStatus() {
        if (!vm.isLoggedIn) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}