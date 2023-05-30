package com.sysoliatina.jewelrystore.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.sysoliatina.jewelrystore.data.client.ClientRepository
import com.sysoliatina.jewelrystore.data.common.CommonRepository
import com.sysoliatina.jewelrystore.data.moderator.ModeratorRepository
import com.sysoliatina.jewelrystore.domain.DataState
import com.sysoliatina.jewelrystore.models.AddProductReq
import com.sysoliatina.jewelrystore.models.Product
import com.sysoliatina.jewelrystore.models.ProductsWithType
import com.sysoliatina.jewelrystore.models.WishList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientProductsViewModel @Inject constructor(
    private val clientRepository: ClientRepository,
) : BaseViewModel<List<ProductsWithType>>() {

    private val _uiStateProduct = MutableStateFlow<DataState<List<Product>>>(DataState.Initial)
    val uiStateProduct: StateFlow<DataState<List<Product>>> = _uiStateProduct.asStateFlow()
    var selectedProductTypePosition: Int = 0

    init {
        viewModelScope.launch {
            launchRequest(clientRepository.getProducts())
        }
    }

    fun addCartItem(productId: Int, count: Int) {
        viewModelScope.launch {
            clientRepository.addCartItem(productId, count).collect {
                _uiStateProduct.value = it
            }
        }
    }

    fun addWishItem(productId: Int) {
        viewModelScope.launch {
            launchRequest(clientRepository.addWishItem(productId))
        }
    }

    fun deleteWishItem(productId: Int) {
        viewModelScope.launch {
            launchRequest(clientRepository.deleteWishItemInProducts(productId))
        }
    }
}