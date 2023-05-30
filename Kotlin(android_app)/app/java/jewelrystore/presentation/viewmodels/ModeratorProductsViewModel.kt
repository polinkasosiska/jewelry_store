package com.sysoliatina.jewelrystore.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.sysoliatina.jewelrystore.data.common.CommonRepository
import com.sysoliatina.jewelrystore.data.moderator.ModeratorRepository
import com.sysoliatina.jewelrystore.domain.DataState
import com.sysoliatina.jewelrystore.models.AddProductReq
import com.sysoliatina.jewelrystore.models.ProductsWithType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModeratorProductsViewModel @Inject constructor(
    private val moderatorRepository: ModeratorRepository,
    private val commonRepository: CommonRepository
) : BaseViewModel<List<ProductsWithType>>() {

    var selectedProductTypePosition: Int = 0

    init {
        viewModelScope.launch {
            launchRequest(commonRepository.getProducts())
        }
    }

    fun addProduct(
        productTypeId: Int, name: String, price: Int, manufactureDate: String,
        material: String, color: String, country: String, weight: Int
    ) {
        val request = AddProductReq(productTypeId, name, price, manufactureDate, material, color, country, weight)
        viewModelScope.launch {
            launchRequest(moderatorRepository.addProduct(request))
        }
    }

    fun deleteProduct(productId: Int): StateFlow<DataState<Int>> {
        val stateFlow = MutableStateFlow<DataState<Int>>(DataState.Loading)
        viewModelScope.launch {
            moderatorRepository.deleteProduct(productId).collect {
                stateFlow.emit(it)
            }
        }
        return stateFlow
    }
}