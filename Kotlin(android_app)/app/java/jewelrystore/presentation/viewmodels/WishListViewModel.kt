package com.sysoliatina.jewelrystore.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.sysoliatina.jewelrystore.data.client.ClientRepository
import com.sysoliatina.jewelrystore.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : BaseViewModel<List<Product>>() {

    fun getWishList() {
        viewModelScope.launch {
            launchRequest(clientRepository.getWishList())
        }
    }
}