package id.io.olebsai.component

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import id.io.olebsai.R

class StatusChipView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    enum class Status(val color: Int, val textColor: Int) {
        RED(R.drawable.chip_status_red, R.color.red_50),
        GREEN(R.drawable.chip_status_green, R.color.green_50),
        BLUE(R.drawable.chip_status_blue, R.color.blue_50),
    }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.StatusChipView, 0, 0).apply {
            try {
                setTextAppearance(R.style.Text_Heading7_Medium)
                val status = getInt(R.styleable.StatusChipView_status, 0)
                setBackgroundResource(
                    when (status) {
                        0x00000001 -> Status.RED.color
                        0x00000002 -> Status.GREEN.color
                        0x00000003 -> Status.BLUE.color
                        else -> R.color.blue_gray_10
                    }
                )
                setTextColor(
                    ContextCompat.getColor(
                        context,
                        when (status) {
                            0x00000001 -> Status.RED.textColor
                            0x00000002 -> Status.GREEN.textColor
                            0x00000003 -> Status.BLUE.textColor
                            else -> R.color.blue_gray_50
                        }
                    )
                )
            } finally {
                recycle()
            }
        }
    }

    fun setStatus(status: Status, text: String) {
        setBackgroundResource(status.color)
        setTextColor(ContextCompat.getColor(context, status.textColor))
        this.text = text
    }
}