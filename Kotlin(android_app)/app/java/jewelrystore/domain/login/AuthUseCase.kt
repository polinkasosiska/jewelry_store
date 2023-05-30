package com.sysoliatina.jewelrystore.domain.login

import androidx.core.util.PatternsCompat
import com.sysoliatina.jewelrystore.R
import com.sysoliatina.jewelrystore.common.AccountSession
import com.sysoliatina.jewelrystore.data.common.CommonRepository
import com.sysoliatina.jewelrystore.domain.DataState
import com.sysoliatina.jewelrystore.models.PrivateUser
import com.sysoliatina.jewelrystore.sharedPreferences.PrefsUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: CommonRepository,
    private val prefsUtils: PrefsUtils
) {

    suspend operator fun invoke(email: String, password: String): Flow<LoginState<PrivateUser>> {
        if (email.isEmpty()) {
            return flow { emit(LoginState.EmailError(R.string.error_empty_field)) }
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            return flow { emit(LoginState.EmailError(R.string.error_invalid_email)) }
        } else if (password.isEmpty()) {
            return flow { emit(LoginState.PasswordError(R.string.error_empty_field)) }
        } else {
            return auth(email, password)
        }
    }

    private suspend fun auth(email: String, password: String): Flow<LoginState<PrivateUser>> {
        val stateFlow = MutableStateFlow<LoginState<PrivateUser>>(LoginState.Loading)
        val authRequest =
            "grant_type=&username=$email&password=$password&scope=&client_id=&client_secret="
        val call = repository.auth(authRequest)
        call.collect {
            if (it is DataState.Initial) {
                stateFlow.emit(LoginState.Initial)
            } else if (it is DataState.Loading) {
                stateFlow.emit(LoginState.Loading)
            } else if (it is DataState.Success) {
                saveAuthData(email, password)
                createSession(it.data)
                stateFlow.emit(LoginState.Success(it.data))
            } else if (it is DataState.Error) {
                stateFlow.emit(LoginState.Error(it.exception, it.code))
            }
        }
        return stateFlow
    }

    private fun createSession(privateUser: PrivateUser) {
        AccountSession.instance.token = privateUser.token
        AccountSession.instance.userId = privateUser.id
        AccountSession.instance.email = privateUser.email
        AccountSession.instance.fullName = privateUser.fullName
    }

    private fun saveAuthData(email: String, password: String) {
        prefsUtils.setEmail(email)
        prefsUtils.setPassword(password)
    }
}