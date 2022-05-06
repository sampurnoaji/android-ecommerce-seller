package id.io.android.seller.presentation.user.otp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(): ViewModel() {

    var otp = ""

    fun onOtpChanged(otp: String) {
        this.otp = otp
    }
}