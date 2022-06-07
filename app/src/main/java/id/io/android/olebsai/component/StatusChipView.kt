package id.io.android.olebsai.component

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import id.io.android.olebsai.R

class StatusChipView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    enum class Status(val color: Int, val textColor: Int) {
        RED(R.drawable.chip_order_status_red, R.color.red_primary),
        GREEN(R.drawable.chip_order_status_green, R.color.green_primary),
    }


    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.StatusChipView, 0, 0).apply {
            try {
                TextViewCompat.setTextAppearance(this@StatusChipView, R.style.TextView_ChipStatus)
                val status = getInt(R.styleable.StatusChipView_status, 0)
                setBackgroundResource(
                    if (status == 1) Status.GREEN.color
                    else Status.RED.color
                )
                setTextColor(
                    ContextCompat.getColor(
                        context, if (status == 1) Status.GREEN.textColor
                        else Status.RED.textColor
                    )
                )
            } finally {
                recycle()
            }
        }
    }
}