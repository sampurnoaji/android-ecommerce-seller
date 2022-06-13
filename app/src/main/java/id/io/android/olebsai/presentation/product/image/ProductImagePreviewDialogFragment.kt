package id.io.android.olebsai.presentation.product.image

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import coil.load
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ItemProductImagePreviewBinding

class ProductImagePreviewDialogFragment : DialogFragment(R.layout.item_product_image_preview) {

    private var _binding: ItemProductImagePreviewBinding? = null
    private val binding get() = _binding!!

    private var image: String = ""

    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemProductImagePreviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.apply {
            setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            setBackgroundDrawableResource(android.R.color.transparent)
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(KEY_IMAGE)?.let { image ->
            this.image = image
            binding.imgProduct.load(image)
        }

        binding.btnDelete.setOnClickListener {
            listener?.onDeleteClicked(image)
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val KEY_IMAGE = "image"
        fun showDialog(fragmentManager: FragmentManager, image: String, listener: Listener) {
            val newFragment = ProductImagePreviewDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_IMAGE, image)
                }
                this.listener = listener
            }
            newFragment.show(fragmentManager, newFragment.tag)
        }
    }

    interface Listener {
        fun onDeleteClicked(image: String)
    }
}