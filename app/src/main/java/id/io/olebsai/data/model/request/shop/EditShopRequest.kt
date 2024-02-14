package id.io.olebsai.data.model.request.shop


import com.squareup.moshi.Json

data class EditShopRequest(
    @field:Json(name = "alamat")
    val alamat: String,
    @field:Json(name = "gambarLogo")
    val gambarLogo: String,
    @field:Json(name = "kecamatan")
    val kecamatan: String,
    @field:Json(name = "kecamatanId")
    val kecamatanId: String,
    @field:Json(name = "keterangan")
    val keterangan: String,
    @field:Json(name = "kodePos")
    val kodePos: String,
    @field:Json(name = "kota")
    val kota: String,
    @field:Json(name = "kotaId")
    val kotaId: String,
    @field:Json(name = "namaToko")
    val namaToko: String,
    @field:Json(name = "provinsi")
    val provinsi: String,
    @field:Json(name = "provinsiId")
    val provinsiId: String,
    @field:Json(name = "tokoId")
    val tokoId: String,
    @field:Json(name = "kurir")
    val kurirs: List<Kurir>,
    @field:Json(name = "bank")
    val bank: String,
    @field:Json(name = "namaPemilikRekening")
    val namaPemilikRekening: String,
    @field:Json(name = "noRekening")
    val noRekening: String,
) {
    data class Kurir(
        @field:Json(name = "kodeKurir")
        val kodeKurir: String,
        @field:Json(name = "namaKurir")
        val namaKurir: String,
    )
}
