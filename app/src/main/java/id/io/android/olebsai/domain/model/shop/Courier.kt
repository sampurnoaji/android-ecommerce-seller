package id.io.android.olebsai.domain.model.shop

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Courier(
    val gambarLogo: String,
    val kodeKurir: String,
    val kurirId: String,
    val namaKurir: String,
    override var isSelected: Boolean = false,
) : Selection, Parcelable

interface Selection {
    var isSelected: Boolean
}
