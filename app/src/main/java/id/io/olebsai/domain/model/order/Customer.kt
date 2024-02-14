package id.io.olebsai.domain.model.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Customer(
    val name: String,
    val address: String,
    val kecamatan: String,
    val kota: String,
    val propinsi: String,
    val kodePos: String,
    val phone: String,
) : Parcelable
