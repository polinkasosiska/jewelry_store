package com.sysoliatina.jewelrystore.data

import com.sysoliatina.jewelrystore.common.Utils
import retrofit2.Response
import com.sysoliatina.jewelrystore.domain.DataState

open class BaseRepository {

    suspend fun <T> apiCall(call: suspend () -> Response<T>): DataState<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return DataState.Success(body)
                }
                return DataState.Empty(response.code())
            }
            val errorMessage = Utils.encodeErrorCode(response.errorBody())
            return error(errorMessage, response.code())
        } catch (e: Exception) {
            return error(e.message ?: " ", -1)
        }
    }

    private fun <T> error(errorMessage: String, code: Int): DataState<T> =
        DataState.Error(errorMessage, code)
}