package id.io.android.olebsai.presentation.user.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentAccountBinding
import id.io.android.olebsai.presentation.user.login.LoginActivity
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class AccountFragment :
    BaseFragment<FragmentAccountBinding, AccountViewModel>(R.layout.fragment_account) {

    override val binding: FragmentAccountBinding by viewBinding(FragmentAccountBinding::bind)
    override val vm: AccountViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionView()
    }

    private fun setupActionView() {
//        binding.tvLogout.setOnClickListener {
//            showDialog(
//                message = resources.getString(R.string.logout_message),
//                negativeButton = resources.getString(R.string.out),
//                negativeAction = {
//                    vm.logout()
//                    val intent = Intent(requireActivity(), LoginActivity::class.java)
//                    startActivity(intent)
//                    requireActivity().finish()
//                },
//                positiveButton = resources.getString(R.string.cancel),
//            )
//        }
    }
}