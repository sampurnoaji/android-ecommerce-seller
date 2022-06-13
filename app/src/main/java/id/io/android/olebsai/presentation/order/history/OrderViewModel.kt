package id.io.android.olebsai.presentation.order.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.domain.model.order.OrderDetail
import id.io.android.olebsai.domain.repository.OrderRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository,
) : ViewModel() {

    private val _activeOrdersResult = MutableLiveData<LoadState<List<Order>>>()
    val activeOrdersResult: LiveData<LoadState<List<Order>>>
        get() = _activeOrdersResult

    private val _doneOrdersResult = MutableLiveData<LoadState<List<Order>>>()
    val doneOrdersResult: LiveData<LoadState<List<Order>>>
        get() = _doneOrdersResult

    private val _orderDetailResult = SingleLiveEvent<LoadState<OrderDetail>>()
    val orderDetailResult: LiveData<LoadState<OrderDetail>>
        get() = _orderDetailResult

    private val _deliverOrderResult = SingleLiveEvent<LoadState<String>>()
    val deliverOrderResult: LiveData<LoadState<String>>
        get() = _deliverOrderResult

    fun getActiveOrders() {
        _activeOrdersResult.value = LoadState.Loading
        viewModelScope.launch {
            _activeOrdersResult.value = repository.getActiveOrders()
        }
    }

    fun getDoneOrders() {
        _doneOrdersResult.value = LoadState.Loading
        viewModelScope.launch {
            _doneOrdersResult.value = repository.getDoneOrders()
        }
    }

    fun getOrderDetail(headerId: String) {
        _orderDetailResult.value = LoadState.Loading
        viewModelScope.launch {
            _orderDetailResult.value = repository.getOrderDetail(headerId)
        }
    }

    fun deliverOrder(headerId: String, nomorResi: String) {
        _deliverOrderResult.value = LoadState.Loading
        viewModelScope.launch {
            _deliverOrderResult.value = repository.deliverOrder(headerId, nomorResi)
        }
    }
}