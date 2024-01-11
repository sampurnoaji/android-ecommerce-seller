package id.io.android.olebsai.presentation.product.detail

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.io.android.olebsai.databinding.DialogUpdateStockBinding

class UpdateProductStockDialog(
    context: Context,
    stock: Int,
    callback: (stock: Int) -> Unit = {},
) : MaterialAlertDialogBuilder(context) {

    private val binding = DialogUpdateStockBinding.inflate(LayoutInflater.from(context))
    private var dialog: AlertDialog

    private var initialStockQty: Int = 0
    private var stockQty: Int = 0

    init {
        dialog = MaterialAlertDialogBuilder(context).create().apply {
            setView(binding.root)
        }

        initialStockQty = stock
        stockQty = stock
        binding.etStock.setText(initialStockQty.toString())

        binding.btnAdd.setOnClickListener {
            stockQty++
            binding.etStock.setText(stockQty.toString())
        }

        binding.btnMinus.setOnClickListener {
            stockQty--
            binding.etStock.setText(stockQty.toString())
        }

        binding.btnUpdate.setOnClickListener {
            with(binding.etStock.text) {
                if (this.isNullOrEmpty()) {
                    return@setOnClickListener
                }

                if (stockQty == initialStockQty) {
                    dialog.dismiss()
                    return@setOnClickListener
                }

                callback(this.trim().toString().toInt())
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