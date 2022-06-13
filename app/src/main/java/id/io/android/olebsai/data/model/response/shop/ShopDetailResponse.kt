package id.io.android.olebsai.data.model.response.shop


import com.squareup.moshi.Json
import id.io.android.olebsai.domain.model.shop.Courier
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
    @field:Json(name = "kotaId")
    val kotaId: String? = null,
    @field:Json(name = "namaToko")
    val namaToko: String? = null,
    @field:Json(name = "provinsi")
    val provinsi: String? = null,
    @field:Json(name = "provinsiId")
    val provinsiId: String? = null,
    @field:Json(name = "status")
    val status: String? = null,
    @field:Json(name = "tokoId")
    val tokoId: String? = null,
    @field:Json(name = "userId")
    val userId: String? = null,
    @field:Json(name = "kecamatan")
    val kecamatan: String? = null,
    @field:Json(name = "kecamatanId")
    val kecamatanId: String? = null,
    @field:Json(name = "kurir")
    val kurir: List<CouriersResponse.CourierResponse>? = null,
) {
    fun toDomain() = ShopDetail(
        id = tokoId.orEmpty(),
        userId = userId.orEmpty(),
        address = alamat.orEmpty(),
        logo = gambarLogo.orEmpty(),
        note = keterangan.orEmpty(),
        postalCode = kodePos.orEmpty(),
        district = kota.orEmpty(),
        districtId = kotaId.orEmpty(),
        name = namaToko.orEmpty(),
        province = provinsi.orEmpty(),
        provinceId = provinsiId.orEmpty(),
        status = status.orEmpty(),
        subDistrict = kecamatan.orEmpty(),
        subDistrictId = kecamatanId.orEmpty(),
        couriers = kurir?.map {
            Courier(
                namaKurir = it.namaKurir.orEmpty(),
                kodeKurir = it.kodeKurir.orEmpty(),
                gambarLogo = it.gambarLogo.toString(),
                kurirId = it.kurirId.orEmpty()
            )
        }.orEmpty()
    )
}