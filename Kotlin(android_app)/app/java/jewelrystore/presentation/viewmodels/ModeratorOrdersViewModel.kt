package com.sysoliatina.jewelrystore.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.sysoliatina.jewelrystore.data.common.CommonRepository
import com.sysoliatina.jewelrystore.data.moderator.ModeratorRepository
import com.sysoliatina.jewelrystore.domain.DataState
import com.sysoliatina.jewelrystore.models.Order
import com.sysoliatina.jewelrystore.models.ProductType
import com.sysoliatina.jewelrystore.models.enums.OrderStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModeratorOrdersViewModel @Inject constructor(
    private val moderatorRepository: ModeratorRepository
) : BaseViewModel<List<Order>>() {

    init {
        viewModelScope.launch {
            launchRequest(moderatorRepository.getAllOrders())
        }
    }

    fun updateOrderStatus(orderId: Int, orderStatus: OrderStatus) {
        viewModelScope.launch {
            launchRequest(moderatorRepository.updateOrderStatus(orderId, orderStatus))
        }
    }
}