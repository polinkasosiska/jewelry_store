package com.sysoliatina.jewelrystore.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.sysoliatina.jewelrystore.domain.login.AuthIfPossibleUseCase
import com.sysoliatina.jewelrystore.domain.login.AuthUseCase
import com.sysoliatina.jewelrystore.models.PrivateUser
import com.sysoliatina.jewelrystore.domain.login.LoginState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val authIfPossibleUseCase: AuthIfPossibleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginState<PrivateUser>>(LoginState.Loading)
    val uiState: StateFlow<LoginState<PrivateUser>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authIfPossibleUseCase().collect {
                _uiState.value = it
            }
        }
    }

    fun auth(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginState.Loading
            authUseCase(email, password).collect {
                _uiState.value = it
            }
        }
    }
}