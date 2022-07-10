package id.io.android.olebsai.data.model.response.shop


import com.squareup.moshi.Json
import id.io.android.olebsai.domain.model.shop.ShopDetail

data class ShopDetailResponse(
    @field:Json(name = "alamat")
    val alamat: String? = null,
    @field:Json(name = "gambarLogo")
    val gambarLogo: String? = null,
    @field:Json(name = "keterangan")
    val keterangan: String? = null,
    @field:Json(name = "kodePos")
    val kodePos: String? = null,
    @field:Json(name = "kota")
    val kota: String? = null,
    @field:Json(name = "namaToko")
    val namaToko: String? = null,
    @field:Json(name = "provinsi")
    val provinsi: String? = null,
    @field:Json(name = "tokoId")
    val tokoId: String? = null,
    @field:Json(name = "userId")
    val userId: String? = null
) {
    fun toDomain() = ShopDetail(
        id = tokoId.orEmpty(),
        userId = userId.orEmpty(),
        address = alamat.orEmpty(),
        logo = gambarLogo.orEmpty(),
        note = keterangan.orEmpty(),
        postalCode = kodePos.orEmpty(),
        city = kota.orEmpty(),
        name = namaToko.orEmpty(),
        province = provinsi.orEmpty(),
    )
}