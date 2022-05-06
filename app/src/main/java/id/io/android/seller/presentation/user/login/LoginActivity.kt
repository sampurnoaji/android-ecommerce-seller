package id.io.android.seller.presentation.user.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.seller.R
import id.io.android.seller.core.BaseActivity
import id.io.android.seller.databinding.ActivityLoginBinding
import id.io.android.seller.presentation.MainActivity
import id.io.android.seller.presentation.user.register.RegisterActivity
import id.io.android.seller.util.UserConstant
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
        binding.etUsername.doOnTextChanged { text, _, _, _ ->
            vm.onUsernameChanged(text.toString())
            binding.tvError.visibility = View.GONE
        }
        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            vm.onPasswordChanged(text.toString())
            binding.tvError.visibility = View.GONE
        }
        
        binding.btnLogin.setOnClickListener {
            if (vm.username.isEmpty()) {
                setErrorText(getString(R.string.register_error_fill_form))
                return@setOnClickListener
            }
            if (vm.password.isEmpty() || vm.password.length < UserConstant.PASSWORD_LENGTH) {
                setErrorText(getString(R.string.login_error_password_length))
                return@setOnClickListener
            }
            vm.login()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setErrorText(error: String) {
        binding.tvError.apply {
            visibility = View.VISIBLE
            text = error
        }
    }
}