package com.sysoliatina.jewelrystore.data.client

import javax.inject.Inject

class ClientDataSource @Inject constructor(private val clientService: ClientService) {
    suspend fun addWishItem(productId: Int) = clientService.addWishItem(productId)
    suspend fun getWishList() = clientService.getWishList()
    suspend fun getProducts() = clientService.getProducts()
    suspend fun deleteWishItem(productId: Int) = clientService.deleteWishItem(productId)
    suspend fun deleteWishItemInProducts(productId: Int) = clientService.deleteWishItemInProducts(productId)
    suspend fun addCartItem(productId: Int, count: Int) = clientService.addCartItem(productId, count)
    suspend fun getCartList() = clientService.getCartList()
    suspend fun deleteCartItem(productId: Int) = clientService.deleteCartItem(productId)
    suspend fun addOrder() = clientService.addOrder()
    suspend fun getOrders() = clientService.getOrders()
    suspend fun getOrder(orderId: Int) = clientService.getOrder(orderId)
    suspend fun cancelOrder(orderId: Int) = clientService.cancelOrder(orderId)
}