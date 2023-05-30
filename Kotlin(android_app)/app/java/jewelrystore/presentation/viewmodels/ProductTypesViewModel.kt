package com.sysoliatina.jewelrystore.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.sysoliatina.jewelrystore.data.common.CommonRepository
import com.sysoliatina.jewelrystore.data.moderator.ModeratorRepository
import com.sysoliatina.jewelrystore.domain.DataState
import com.sysoliatina.jewelrystore.models.ProductType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductTypesViewModel @Inject constructor(
    private val moderatorRepository: ModeratorRepository,
    private val commonRepository: CommonRepository
) : BaseViewModel<List<ProductType>>() {

    init {
        viewModelScope.launch {
            launchRequest(commonRepository.getProductTypes())
        }
    }

    fun addProductType(name: String) {
        viewModelScope.launch {
            launchRequest(moderatorRepository.addProductType(name))
        }
    }

    fun deleteProductType(productTypeId: Int): StateFlow<DataState<Int>> {
        val stateFlow = MutableStateFlow<DataState<Int>>(DataState.Loading)
        viewModelScope.launch {
            moderatorRepository.deleteProductType(productTypeId).collect {
                stateFlow.emit(it)
            }
        }
        return stateFlow
    }
}