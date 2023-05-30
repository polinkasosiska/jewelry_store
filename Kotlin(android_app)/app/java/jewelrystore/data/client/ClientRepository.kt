package com.sysoliatina.jewelrystore.data.client

import com.sysoliatina.jewelrystore.data.BaseRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientRepository @Inject constructor(
    private val clientDataSource: ClientDataSource
) : BaseRepository() {

    suspend fun addWishItem(productId: Int) =
        flow { emit(apiCall { clientDataSource.addWishItem(productId) }) }

    suspend fun getWishList() =
        flow { emit(apiCall { clientDataSource.getWishList() }) }

    suspend fun getProducts() =
        flow { emit(apiCall { clientDataSource.getProducts() }) }

    suspend fun deleteWishItem(productId: Int) =
        flow { emit(apiCall { clientDataSource.deleteWishItem(productId) }) }

    suspend fun deleteWishItemInProducts(productId: Int) =
        flow { emit(apiCall { clientDataSource.deleteWishItemInProducts(productId) }) }

    suspend fun addCartItem(productId: Int, count: Int) =
        flow { emit(apiCall { clientDataSource.addCartItem(productId, count) }) }

    suspend fun getCartList() =
        flow { emit(apiCall { clientDataSource.getCartList() }) }

    suspend fun deleteCartItem(productId: Int) =
        flow { emit(apiCall { clientDataSource.deleteCartItem(productId) }) }

    suspend fun addOrder() =
        flow { emit(apiCall { clientDataSource.addOrder() }) }

    suspend fun getOrders() =
        flow { emit(apiCall { clientDataSource.getOrders() }) }

    suspend fun getOrder(orderId: Int) =
        flow { emit(apiCall { clientDataSource.getOrder(orderId) }) }

    suspend fun cancelOrder(orderId: Int) =
        flow { emit(apiCall { clientDataSource.cancelOrder(orderId) }) }
}