package id.io.android.seller.presentation.user.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.seller.R
import id.io.android.seller.core.BaseActivity
import id.io.android.seller.databinding.ActivityRegisterBinding
import id.io.android.seller.presentation.user.otp.OtpActivity
import id.io.android.seller.util.UserConstant.PASSWORD_LENGTH
import id.io.android.seller.util.viewBinding

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {

    override val binding: ActivityRegisterBinding by viewBinding(ActivityRegisterBinding::inflate)
    override val vm: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar(binding.topAppBar, showHomeAsUp = true)
        setupActionView()
    }

    private fun setupActionView() {
        binding.etUsername.doOnTextChanged { text, _, _, _ ->
            vm.onUsernameChanged(text.toString())
            binding.tvError.visibility = View.GONE
        }
        binding.etShopName.doOnTextChanged { text, _, _, _ ->
            vm.onShopNameChanged(text.toString())
            binding.tvError.visibility = View.GONE
        }
        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            vm.onPasswordChanged(text.toString())
            binding.tvError.visibility = View.GONE
        }

        binding.btnRegister.setOnClickListener {
            if (vm.username.isEmpty()) {
                setErrorText(getString(R.string.register_error_fill_form))
                return@setOnClickListener
            }
            if (vm.shopName.isEmpty()) {
                setErrorText(getString(R.string.register_error_fill_form))
                return@setOnClickListener
            }
            if (vm.password.isEmpty() || vm.password.length < PASSWORD_LENGTH) {
                setErrorText(getString(R.string.login_error_password_length))
                return@setOnClickListener
            }
            val intent = Intent(this, OtpActivity::class.java)
            startActivity(intent)
        }
        binding.tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun setErrorText(error: String) {
        binding.tvError.apply {
            visibility = View.VISIBLE
            text = error
        }
    }
}