package com.sysoliatina.jewelrystore.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.sysoliatina.jewelrystore.domain.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel<T> : ViewModel() {
    private val _uiState = MutableStateFlow<DataState<T>>(DataState.Initial)
    val uiState: StateFlow<DataState<T>> = _uiState.asStateFlow()

    protected suspend fun launchRequest(request: Flow<DataState<T>>) {
        _uiState.value = DataState.Loading
        return request.collect {
            _uiState.value = it
        }
    }
}