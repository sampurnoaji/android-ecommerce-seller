package id.io.android.olebsai.presentation.order.detail

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.io.android.olebsai.databinding.DialogInputNomorResiBinding

class InputNomorResiDialog(
    context: Context,
    callback: (String) -> Unit = {},
) : MaterialAlertDialogBuilder(context) {

    private val binding = DialogInputNomorResiBinding.inflate(LayoutInflater.from(context))
    private var dialog: AlertDialog

    init {
        dialog = MaterialAlertDialogBuilder(context).create().apply {
            setView(binding.root)
        }

        binding.btnSend.setOnClickListener {
            with(binding.etResi.text) {
                if (this.isNullOrBlank()) return@setOnClickListener
                callback(this.trim().toString())
                dialog.dismiss()
            }
        }

        binding.btnNegative.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun show(): AlertDialog {
        dialog.show()
        return dialog
    }
}