package com.sysoliatina.jewelrystore.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.sysoliatina.jewelrystore.data.client.ClientRepository
import com.sysoliatina.jewelrystore.domain.DataState
import com.sysoliatina.jewelrystore.models.Product
import com.sysoliatina.jewelrystore.models.WishList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : BaseViewModel<List<Product>>() {

    fun getCartList() {
        viewModelScope.launch {
            launchRequest(clientRepository.getCartList())
        }
    }

    fun addOrder() {
        viewModelScope.launch {
            launchRequest(clientRepository.addOrder())
        }
    }

    fun deleteCartItem(productId: Int): StateFlow<DataState<WishList>> {
        val stateFlow = MutableStateFlow<DataState<WishList>>(DataState.Loading)
        viewModelScope.launch {
            clientRepository.deleteCartItem(productId).collect {
                stateFlow.emit(it)
            }
        }
        return stateFlow
    }
}