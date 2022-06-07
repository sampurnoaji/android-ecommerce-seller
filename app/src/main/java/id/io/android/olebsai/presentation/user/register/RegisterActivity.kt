package id.io.android.olebsai.presentation.user.register

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityRegisterBinding
import id.io.android.olebsai.util.UserConstant.PASSWORD_LENGTH
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {

    override val binding: ActivityRegisterBinding by viewBinding(ActivityRegisterBinding::inflate)
    override val vm: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar(binding.topAppBar, showHomeAsUp = true)
        setupActionView()
        observeRegisterResult()
    }

    private fun setupActionView() {
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            vm.onEmailChanged(text.toString())
            binding.tvError.visibility = View.GONE
        }
        binding.etName.doOnTextChanged { text, _, _, _ ->
            vm.onNameChanged(text.toString())
            binding.tvError.visibility = View.GONE
        }
        binding.etPhoneNumber.doOnTextChanged { text, _, _, _ ->
            vm.onPhoneNumberChanged(text.toString())
            binding.tvError.visibility = View.GONE
        }
        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            vm.onPasswordChanged(text.toString())
            binding.tvError.visibility = View.GONE
        }

        binding.btnRegister.setOnClickListener {
            if (vm.email.isEmpty()) {
                setErrorText(getString(R.string.register_error_fill_form))
                return@setOnClickListener
            }
            if (vm.name.isEmpty()) {
                setErrorText(getString(R.string.register_error_fill_form))
                return@setOnClickListener
            }
            if (vm.phoneNumber.isEmpty()) {
                setErrorText(getString(R.string.register_error_fill_form))
                return@setOnClickListener
            }
            if (vm.password.isEmpty() || vm.password.length < PASSWORD_LENGTH) {
                setErrorText(getString(R.string.login_error_password_length))
                return@setOnClickListener
            }
            vm.register()
        }
        binding.tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun observeRegisterResult() {
        vm.register.observe(
            loadingProgressBar = binding.pbLoading,
            success = {
                showDialog(
                    message = it,
                    positiveButton = getString(R.string.login),
                    positiveAction = {
                        finish()
                    })
            },
            error = {
                showDialog(
                    message = it.orEmpty(),
                    positiveButton = getString(android.R.string.ok)
                )
            },
        )
    }

    private fun setErrorText(error: String) {
        binding.tvError.apply {
            visibility = View.VISIBLE
            text = error
        }
    }
}