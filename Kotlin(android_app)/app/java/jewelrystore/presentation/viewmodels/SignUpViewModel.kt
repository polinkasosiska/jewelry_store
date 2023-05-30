package com.sysoliatina.jewelrystore.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sysoliatina.jewelrystore.common.AccountSession
import com.sysoliatina.jewelrystore.data.common.CommonRepository
import com.sysoliatina.jewelrystore.domain.DataState
import com.sysoliatina.jewelrystore.models.ClientData
import com.sysoliatina.jewelrystore.models.PrivateUser
import com.sysoliatina.jewelrystore.models.SignUpReq
import com.sysoliatina.jewelrystore.models.UserData
import com.sysoliatina.jewelrystore.sharedPreferences.PrefsUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: CommonRepository,
    private val prefsUtils: PrefsUtils
) : ViewModel(){

    private val _uiState = MutableStateFlow<DataState<PrivateUser>>(DataState.Initial)
    val uiState: StateFlow<DataState<PrivateUser>> = _uiState.asStateFlow()

    fun signUp(fullName: String, address: String, phone: String, email: String, password: String) {
        _uiState.value = DataState.Loading
        viewModelScope.launch {
            val userData = UserData(email, fullName, password)
            val clientData = ClientData(address, phone)
            repository.signUp(SignUpReq(userData, clientData)).collect {
                if (it is DataState.Success) {
                    val privateUser = it.data
                    AccountSession.instance.token = privateUser.token
                    AccountSession.instance.userId = privateUser.id
                    AccountSession.instance.email = privateUser.email
                    AccountSession.instance.fullName = privateUser.fullName
                    prefsUtils.setEmail(email)
                    prefsUtils.setPassword(password)
                }
                _uiState.value = it
            }
        }
    }
}