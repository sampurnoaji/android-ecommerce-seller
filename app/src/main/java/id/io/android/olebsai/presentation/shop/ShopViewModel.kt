package id.io.android.olebsai.presentation.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(): ViewModel() {

    private val _shops = MutableLiveData<List<String>>()
    val shops: LiveData<List<String>>
        get() = _shops

    init {
        _shops.value = listOf("", "")
    }
}