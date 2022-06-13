package id.io.android.olebsai.util.textfield

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import id.io.android.olebsai.util.addThousandSeparator

class ThousandTextWatcher(
    private val editText: EditText,
    private val onValueChanged: ((Long?) -> Unit)? = null,
): TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        editText.removeTextChangedListener(this)
        val value: Long? = s.toString().replace(".", "").toLongOrNull()

        // if onValueChanged filled, you must send the data to view model and observe it to update EditText value
        onValueChanged?.invoke(value) ?: editText.setText(value?.addThousandSeparator())

        editText.setSelection(editText.text.toString().length)
        editText.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable?) {}
}