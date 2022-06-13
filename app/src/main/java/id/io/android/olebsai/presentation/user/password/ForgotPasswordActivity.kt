package id.io.android.olebsai.presentation.user.password

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityForgotPasswordBinding
import id.io.android.olebsai.util.clearError
import id.io.android.olebsai.util.isValidEmail
import id.io.android.olebsai.util.setErrorText
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class ForgotPasswordActivity :
    BaseActivity<ActivityForgotPasswordBinding, ForgotPasswordViewModel>() {

    override val binding by viewBinding(ActivityForgotPasswordBinding::inflate)
    override val vm: ForgotPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionView()
        observeViewModel()
    }

    private fun setupActionView() {
        binding.imgBack.setOnClickListener { onBackPressed() }

        binding.etEmail.doOnTextChanged { _, _, _, _ ->
            binding.tilEmail.clearError()
        }

        binding.btnSend.setOnClickListener {
            val email = binding.etEmail.text?.trim().toString()
            if (!email.isValidEmail()) {
                binding.tilEmail.setErrorText(R.string.form_email_invalid)
                return@setOnClickListener
            }
            Dialog(
                context = this,
                message = "Cek email kamu",
                positiveButtonText = getString(R.string.close),
                positiveAction = { finish() }
            ).show()
        }
    }

    private fun observeViewModel() {

    }
}