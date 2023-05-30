package com.sysoliatina.jewelrystore.data.moderator

import com.sysoliatina.jewelrystore.models.AddProductReq
import com.sysoliatina.jewelrystore.models.enums.OrderStatus
import javax.inject.Inject

class ModeratorDataSource @Inject constructor(private val commonService: ModeratorService) {
    suspend fun addProductType(name: String) = commonService.addProductType(name)
    suspend fun deleteProductType(productTypeId: Int) =
        commonService.deleteProductType(productTypeId)
    suspend fun addProduct(request: AddProductReq) = commonService.addProduct(request)
    suspend fun updateProduct(productId: Int, request: AddProductReq) =
        commonService.updateProduct(productId, request)
    suspend fun deleteProduct(productId: Int) = commonService.deleteProduct(productId)
    suspend fun getAllOrders() = commonService.getAllOrders()
    suspend fun getOrder(orderId: Int) = commonService.getOrder(orderId)
    suspend fun updateOrderStatus(orderId: Int, status: OrderStatus) =
        commonService.updateOrderStatus(orderId, status)
}