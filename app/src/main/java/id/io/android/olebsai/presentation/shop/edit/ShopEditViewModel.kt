package id.io.android.olebsai.presentation.shop.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.shop.Courier
import id.io.android.olebsai.domain.model.shop.EditShopParams
import id.io.android.olebsai.domain.model.shop.ShopDetail
import id.io.android.olebsai.domain.model.user.Address
import id.io.android.olebsai.domain.repository.ShopRepository
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ShopEditViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val shopRepository: ShopRepository,
) : ViewModel() {

    private val _editShopResult = SingleLiveEvent<LoadState<String>>()
    val editShopResult: LiveData<LoadState<String>>
        get() = _editShopResult

    private val _subDistrictsResult = MutableLiveData<LoadState<List<Address>>>()
    val subDistrictsResult: LiveData<LoadState<List<Address>>>
        get() = _subDistrictsResult

    private val _couriersResult = MutableLiveData<LoadState<List<Courier>>>()
    val couriersResult: LiveData<LoadState<List<Courier>>>
        get() = _couriersResult

    fun editShop(shop: ShopDetail) {
        _editShopResult.value = LoadState.Loading
        viewModelScope.launch {
            _editShopResult.value = shopRepository.editShop(EditShopParams(
                alamat = shop.address,
                gambarLogo = "",
                keterangan = shop.note,
                kodePos = shop.postalCode,
                kota = shop.district,
                kotaId = shop.districtId,
                namaToko = shop.name,
                provinsi = shop.province,
                provinsiId = shop.provinceId,
                tokoId = shop.id,
                kecamatan = shop.subDistrict,
                kecamatanId = shop.subDistrictId,
                couriers = shop.couriers,
                bank = shop.bank,
                noRekening = shop.noRekening,
                namaPemilikRekening = shop.namaPemilikRekening,
            ))
        }
    }

    fun getSubDistricts(districtId: String) {
        _subDistrictsResult.value = LoadState.Loading
        viewModelScope.launch {
            _subDistrictsResult.value = userRepository.getSubDistricts(districtId)
        }
    }

    fun getCouriers() {
        _couriersResult.value = LoadState.Loading
        viewModelScope.launch {
            _couriersResult.value = shopRepository.getCouriers()
        }
    }
}