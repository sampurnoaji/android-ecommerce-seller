package id.io.olebsai.presentation.order.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.olebsai.data.source.remote.order.OrderPagingSource
import id.io.olebsai.domain.model.order.Order
import id.io.olebsai.domain.model.order.OrderDetail
import id.io.olebsai.domain.repository.OrderRepository
import id.io.olebsai.util.LoadState
import id.io.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository,
) : ViewModel() {

    private val _activeOrdersResult = MutableLiveData<LoadState<List<Order>>>()
    val activeOrdersResult: LiveData<LoadState<List<Order>>>
        get() = _activeOrdersResult

    val doneOrders: Flow<PagingData<Order>> = Pager(
        config = PagingConfig(pageSize = OrderPagingSource.ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { repository.getOrderPagingSource() }
    ).flow.cachedIn(viewModelScope)

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