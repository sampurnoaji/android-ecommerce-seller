package id.io.android.seller.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


abstract class BaseActivity<B : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    abstract val binding: B
    abstract val vm: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun setupToolbar(toolbar: Toolbar, showHomeAsUp: Boolean = false) {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(showHomeAsUp)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

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
        MaterialAlertDialogBuilder(this)
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