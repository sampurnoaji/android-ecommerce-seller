package id.io.android.olebsai.presentation.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.shop.ShopDetail
import id.io.android.olebsai.domain.usecase.shop.ShopUseCases
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ShopViewModel @Inject constructor(private val shopUseCases: ShopUseCases) : ViewModel() {

    private val _shopDetailResult = SingleLiveEvent<LoadState<ShopDetail>>()
    val shopDetailResult: LiveData<LoadState<ShopDetail>>
        get() = _shopDetailResult

    fun getShopCached(): ShopDetail? = shopUseCases.getShopCachedUseCase()

    init {
        getShopDetail()
    }

    fun getShopDetail() {
        _shopDetailResult.value = LoadState.Loading
        viewModelScope.launch {
            _shopDetailResult.value = shopUseCases.getShopDetailUseCase()
        }
    }

    fun saveShop(shopDetail: ShopDetail) {
        shopUseCases.saveShopUseCase(shopDetail)
    }
}