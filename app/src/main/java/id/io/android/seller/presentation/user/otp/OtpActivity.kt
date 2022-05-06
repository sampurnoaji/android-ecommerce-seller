package id.io.android.seller.presentation.user.otp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.seller.R
import id.io.android.seller.core.BaseActivity
import id.io.android.seller.databinding.ActivityOtpBinding
import id.io.android.seller.presentation.user.login.LoginActivity
import id.io.android.seller.util.UserConstant.OTP_LENGTH
import id.io.android.seller.util.viewBinding

@AndroidEntryPoint
class OtpActivity : BaseActivity<ActivityOtpBinding, OtpViewModel>() {

    override val binding: ActivityOtpBinding by viewBinding(ActivityOtpBinding::inflate)
    override val vm: OtpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar(binding.topAppBar, showHomeAsUp = true)
        setupActionView()
    }

    private fun setupActionView() {
        binding.etOtp.doOnTextChanged { text, _, _, _ ->
            vm.onOtpChanged(text.toString())
            binding.tvError.visibility = View.GONE
        }

        binding.btnVerify.setOnClickListener {
            if (vm.otp.length < OTP_LENGTH) {
                binding.tvError.apply {
                    visibility = View.VISIBLE
                    text = getString(R.string.otp_error_six_digit)
                }
                return@setOnClickListener
            }
            showDialog(
                message = getString(R.string.register_success),
                positiveButton = getString(android.R.string.ok),
                positiveAction = {
                    val intent = Intent(this, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                }
            )
        }
    }
}