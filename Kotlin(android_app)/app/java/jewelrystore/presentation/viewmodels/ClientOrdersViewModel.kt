package com.sysoliatina.jewelrystore.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.sysoliatina.jewelrystore.data.client.ClientRepository
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
class ClientOrdersViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : BaseViewModel<List<Order>>() {

    fun getOrders() {
        viewModelScope.launch {
            launchRequest(clientRepository.getOrders())
        }
    }

    fun cancelOrder(orderId: Int) {
        viewModelScope.launch {
            launchRequest(clientRepository.cancelOrder(orderId))
        }
    }
}