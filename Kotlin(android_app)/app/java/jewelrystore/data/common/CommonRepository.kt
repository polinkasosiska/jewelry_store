package com.sysoliatina.jewelrystore.data.common

import com.sysoliatina.jewelrystore.data.BaseRepository
import kotlinx.coroutines.flow.flow
import com.sysoliatina.jewelrystore.models.SignUpReq
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonRepository @Inject constructor(
    private val commonDataSource: CommonDataSource
) : BaseRepository() {

    suspend fun auth(request: String) =
        flow { emit(apiCall { commonDataSource.auth(request) }) }

    suspend fun signUp(request: SignUpReq) =
        flow { emit(apiCall { commonDataSource.signUp(request) }) }

    suspend fun getProductTypes() =
        flow { emit(apiCall { commonDataSource.getProductTypes() }) }

    suspend fun getProducts() =
        flow { emit(apiCall { commonDataSource.getProducts() }) }

    suspend fun getProduct(productId: Int) =
        flow { emit(apiCall { commonDataSource.getProduct(productId) }) }
}