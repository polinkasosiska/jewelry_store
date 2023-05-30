package com.sysoliatina.jewelrystore.data.moderator

import com.sysoliatina.jewelrystore.data.BaseRepository
import com.sysoliatina.jewelrystore.models.AddProductReq
import kotlinx.coroutines.flow.flow
import com.sysoliatina.jewelrystore.models.enums.OrderStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModeratorRepository @Inject constructor(
    private val moderatorDataSource: ModeratorDataSource
) : BaseRepository() {

    suspend fun addProductType(name: String) =
        flow { emit(apiCall { moderatorDataSource.addProductType(name) }) }

    suspend fun deleteProductType(productTypeId: Int) =
        flow { emit(apiCall { moderatorDataSource.deleteProductType(productTypeId) }) }

    suspend fun addProduct(request: AddProductReq) =
        flow { emit(apiCall { moderatorDataSource.addProduct(request) }) }

    suspend fun updateProduct(productId: Int, request: AddProductReq) =
        flow { emit(apiCall { moderatorDataSource.updateProduct(productId, request) }) }

    suspend fun deleteProduct(productId: Int) =
        flow { emit(apiCall { moderatorDataSource.deleteProduct(productId) }) }

    suspend fun getAllOrders() =
        flow { emit(apiCall { moderatorDataSource.getAllOrders() }) }

    suspend fun getOrder(orderId: Int) =
        flow { emit(apiCall { moderatorDataSource.getOrder(orderId) }) }

    suspend fun updateOrderStatus(orderId: Int, status: OrderStatus) =
        flow { emit(apiCall { moderatorDataSource.updateOrderStatus(orderId, status) }) }
}