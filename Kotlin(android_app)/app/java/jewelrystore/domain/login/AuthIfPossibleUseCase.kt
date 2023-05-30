package com.sysoliatina.jewelrystore.domain.login

import com.sysoliatina.jewelrystore.models.PrivateUser
import com.sysoliatina.jewelrystore.sharedPreferences.PrefsUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class AuthIfPossibleUseCase @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val prefsUtils: PrefsUtils
) {

    suspend operator fun invoke(): Flow<LoginState<PrivateUser>> {
        val stateFlow = MutableStateFlow<LoginState<PrivateUser>>(LoginState.Loading)
        if (isAuthDataSaved()) {
            return authWithSavedData()
        } else {
            stateFlow.emit(LoginState.Unauthorized)
        }
        return stateFlow
    }

    private fun isAuthDataSaved() = prefsUtils.getEmail().isNotEmpty()

    private suspend fun authWithSavedData(): Flow<LoginState<PrivateUser>> {
        return authUseCase(prefsUtils.getEmail(), prefsUtils.getPassword())
    }
}