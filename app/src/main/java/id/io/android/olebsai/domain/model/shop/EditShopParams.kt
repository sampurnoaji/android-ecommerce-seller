package id.io.android.olebsai.domain.model.shop

import id.io.android.olebsai.data.model.request.shop.EditShopRequest

data class EditShopParams(
    val alamat: String,
    val gambarLogo: String,
    val kecamatan: String,
    val kecamatanId: String,
    val keterangan: String,
    val kodePos: String,
    val kota: String,
    val kotaId: String,
    val namaToko: String,
    val provinsi: String,
    val provinsiId: String,
    val tokoId: String,
    val couriers: List<Courier>,
    val bank: String,
    val namaPemilikRekening: String,
    val noRekening: String,
) {

    fun toRequest() = EditShopRequest(
        alamat = alamat,
        gambarLogo = gambarLogo,
        kecamatan = kecamatan,
        kecamatanId = kecamatanId,
        kodePos = kodePos,
        kota = kota,
        kotaId = kotaId,
        namaToko = namaToko,
        provinsi = provinsi,
        provinsiId = provinsiId,
        tokoId = tokoId,
        keterangan = keterangan,
        kurirs = couriers.map {
            EditShopRequest.Kurir(
                namaKurir = it.namaKurir,
                kodeKurir = it.kodeKurir,
            )
        },
        bank = bank,
        namaPemilikRekening = namaPemilikRekening,
        noRekening = noRekening,
    )
}