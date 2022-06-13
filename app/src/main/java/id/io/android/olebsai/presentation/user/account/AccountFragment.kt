package id.io.android.olebsai.presentation.user.account

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentAccountBinding
import id.io.android.olebsai.presentation.user.login.LoginActivity
import id.io.android.olebsai.util.ui.Dialog
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
        binding.imgSetting.setOnClickListener {
            showSettingMenu(it)
        }
    }

    private fun showSettingMenu(view: View) {
        PopupMenu(requireContext(), view).apply {
            menu.add(Menu.NONE, 1, 1, getString(R.string.logout))
            show()
            setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    1 -> {
                        showLogoutDialog()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun showLogoutDialog() {
        Dialog(
            context = requireContext(),
            message = getString(R.string.logout_message),
            positiveButtonText = getString(R.string.logout),
            positiveAction = {
                vm.logout()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            },
            negativeButtonText = getString(R.string.cancel)
        ).show()
    }
}