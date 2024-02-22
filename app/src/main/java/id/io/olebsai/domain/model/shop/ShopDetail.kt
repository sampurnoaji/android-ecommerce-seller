package id.io.olebsai.domain.model.shop

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopDetail(
    val id: String,
    val userId: String,
    val address: String,
    val logo: String,
    val note: String,
    val postalCode: String,
    val district: String,
    val districtId: String,
    val name: String,
    val province: String,
    val provinceId: String,
    val subDistrict: String,
    val subDistrictId: String,
    val status: String,
    val couriers: List<Courier>,
    val bank: String,
    val namaPemilikRekening: String,
    val noRekening: String,
) : Parcelable {

    fun isApproved() = status.lowercase() == "AKTIF".lowercase()

    fun isStatusRegistrasi() = status.lowercase() == "REGISTRASI".lowercase()
}