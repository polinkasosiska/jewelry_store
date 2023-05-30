package com.sysoliatina.jewelrystore.data.common

import com.sysoliatina.jewelrystore.models.SignUpReq
import javax.inject.Inject

class CommonDataSource @Inject constructor(private val commonService : CommonService) {
    suspend fun auth(request: String) = commonService.auth(request)
    suspend fun signUp(request: SignUpReq) = commonService.signUp(request)
    suspend fun getProductTypes() = commonService.getProductTypes()
    suspend fun getProducts() = commonService.getProducts()
    suspend fun getProduct(productId: Int) = commonService.getProduct(productId)
}