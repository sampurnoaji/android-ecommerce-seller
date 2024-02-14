package id.io.olebsai.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import id.io.olebsai.R
import id.io.olebsai.databinding.ItemDashboardCardBinding


class ItemDashboardCardView(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs) {

    private val binding by lazy {
        ItemDashboardCardBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ItemDashboardCardView, 0, 0
        ).apply {
            try {
                binding.tvTitle.text =
                    getString(R.styleable.ItemDashboardCardView_cardTitle).orEmpty()
                binding.tvValue.text = getString(R.styleable.ItemDashboardCardView_value).orEmpty()
                binding.icon.setBackgroundColor(
                    getInt(
                        R.styleable.ItemDashboardCardView_iconTint,
                        android.R.color.darker_gray
                    )
                )
            } finally {
                recycle()
            }
        }
    }

    fun setTitle(title: String) {
        binding.tvTitle.text = title
    }

    fun setValue(value: String) {
        binding.tvValue.text = value
    }
}