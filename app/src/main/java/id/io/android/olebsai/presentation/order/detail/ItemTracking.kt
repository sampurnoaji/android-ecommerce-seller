package id.io.android.olebsai.presentation.order.detail

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import id.io.android.olebsai.databinding.ItemTrackingBinding

class ItemTracking(context: Context) : ConstraintLayout(context) {

    private var binding: ItemTrackingBinding

    init {
        binding = ItemTrackingBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setHeader(header: String) {
        binding.tvHeader.text = header
    }

    fun setMessage(message: String) {
        binding.tvMessage.text = message
    }
}