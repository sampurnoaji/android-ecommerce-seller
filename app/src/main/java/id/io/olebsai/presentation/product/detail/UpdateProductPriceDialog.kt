package id.io.olebsai.presentation.product.detail

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.io.olebsai.databinding.DialogUpdatePriceBinding
import id.io.olebsai.util.textfield.ThousandTextWatcher
import okhttp3.internal.toLongOrDefault

class UpdateProductPriceDialog(
    context: Context,
    private val initialNormalPrice: Long,
    private val initialPromoPrice: Long,
    private var isPromoActive: Boolean,
    callback: (normalPrice: Long, promoPrice: Long, isPromoActive: Boolean) -> Unit,
) : MaterialAlertDialogBuilder(context) {

    private val binding = DialogUpdatePriceBinding.inflate(LayoutInflater.from(context))
    private var dialog: AlertDialog

    init {
        dialog = MaterialAlertDialogBuilder(context).create().apply {
            setView(binding.root)
        }

        with(binding.etPrice) {
            addTextChangedListener(ThousandTextWatcher(this))
        }

        with(binding.etPricePromo) {
            addTextChangedListener(ThousandTextWatcher(this))
        }

        binding.etPrice.setText(initialNormalPrice.toString())
        binding.etPricePromo.setText(initialPromoPrice.toString())
        binding.cbIsPromo.isChecked = isPromoActive

        binding.btnUpdate.setOnClickListener {
            with(binding.etPrice) {
                if (text.isNullOrEmpty()) {
                    return@setOnClickListener
                }
            }
            with(binding.etPricePromo) {
                if (text.isNullOrEmpty()) {
                    return@setOnClickListener
                }
            }
            val price = binding.etPrice.text.toString().replace(".", "").toLongOrDefault(0L)
            val promoPrice =
                binding.etPricePromo.text.toString().replace(".", "").toLongOrDefault(0L)
            val isActivePromo = binding.cbIsPromo.isChecked
            callback(price, promoPrice, isActivePromo)
            dialog.dismiss()
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