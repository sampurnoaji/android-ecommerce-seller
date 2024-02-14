package id.io.olebsai.presentation.user.otp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import id.io.olebsai.R
import id.io.olebsai.core.BaseActivity
import id.io.olebsai.databinding.ActivityOtpBinding
import id.io.olebsai.domain.model.user.User
import id.io.olebsai.presentation.MainActivity
import id.io.olebsai.util.UserConstant.OTP_LENGTH
import id.io.olebsai.util.viewBinding

@AndroidEntryPoint
class OtpActivity : BaseActivity<ActivityOtpBinding, OtpViewModel>() {

    override val binding: ActivityOtpBinding by viewBinding(ActivityOtpBinding::inflate)
    override val vm: OtpViewModel by viewModels()

    private val user by lazy { intent?.getParcelableExtra<User>(ARG_KEY_USER) }

    companion object {
        private const val ARG_KEY_USER = "user"

        @JvmStatic
        fun start(context: Context, user: User) {
            val starter = Intent(context, OtpActivity::class.java)
                .putExtra(ARG_KEY_USER, user)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionView()
        observeViewModel()
    }

    private fun observeViewModel() {
        vm.loginWithOtpResult.observe(
            onLoading = {},
            onSuccess = {
                user?.let { user -> vm.saveUser(user) }
                MainActivity.start(this)
                finish()
            },
            onError = {
                showInfoDialog(it?.message.orEmpty())
            }
        )
    }

    private fun setupActionView() {
        binding.imgBack.setOnClickListener { onBackPressed() }

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
            user?.let { user -> vm.loginWithOtp(user.email) }
        }
    }
}