package id.io.android.seller.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


abstract class BaseFragment<B : ViewBinding, VM : ViewModel>(@LayoutRes id: Int) : Fragment(id) {

    abstract val binding: B
    abstract val vm: VM

    fun showDialog(
        title: String? = null,
        message: String,
        neutralButton: String? = null,
        neutralAction: () -> Unit? = {},
        negativeButton: String? = null,
        negativeAction: () -> Unit? = {},
        positiveButton: String? = null,
        positiveAction: () -> Unit? = {}
    ) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(neutralButton) { dialog, which ->
                neutralAction()
            }
            .setNegativeButton(negativeButton) { dialog, which ->
                negativeAction()
            }
            .setPositiveButton(positiveButton) { dialog, which ->
                positiveAction()
            }
            .show()
    }
}