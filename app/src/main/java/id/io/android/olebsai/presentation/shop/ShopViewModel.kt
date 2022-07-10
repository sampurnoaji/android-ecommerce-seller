package id.io.android.olebsai.presentation.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.shop.ShopDetail
import id.io.android.olebsai.domain.usecase.shop.ShopUseCases
import id.io.android.olebsai.util.LoadState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(private val shopUseCases: ShopUseCases) : ViewModel() {

    private val _shop = MutableLiveData<LoadState<ShopDetail>>()
    val shop: LiveData<LoadState<ShopDetail>>
        get() = _shop

    init {
        getShopDetail()
    }

    fun getShopDetail() {
        _shop.value = LoadState.Loading
        viewModelScope.launch {
            _shop.value = shopUseCases.getShopDetailUseCase()
        }
    }
}