package id.io.android.seller.presentation.user.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.seller.core.BaseActivity
import id.io.android.seller.databinding.ActivityLoginBinding
import id.io.android.seller.presentation.MainActivity
import id.io.android.seller.util.viewBinding

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override val binding: ActivityLoginBinding by viewBinding(ActivityLoginBinding::inflate)
    override val vm: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionView()
    }

    private fun setupActionView() {
        binding.btnLogin.setOnClickListener {
            vm.login()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}