package id.io.android.seller.presentation.user.register

import android.os.Bundle
import androidx.activity.viewModels
import id.io.android.seller.R
import id.io.android.seller.core.BaseActivity
import id.io.android.seller.databinding.ActivityRegisterBinding
import id.io.android.seller.util.viewBinding

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {

    override val binding: ActivityRegisterBinding by viewBinding(ActivityRegisterBinding::inflate)
    override val vm: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionView()
    }

    private fun setupActionView() {
        binding.btnRegister.setOnClickListener {
            showDialog(
                message = getString(R.string.register_success),
                positiveButton = getString(android.R.string.ok),
                positiveAction = { finish() }
            )
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }
}