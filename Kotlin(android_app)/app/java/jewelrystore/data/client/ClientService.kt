package com.sysoliatina.jewelrystore.data.client

import com.sysoliatina.jewelrystore.models.*
import retrofit2.Response
import retrofit2.http.*


interface ClientService {

    @POST("wish-list/add")
    suspend fun addWishItem(@Query("product_id") productId: Int): Response<List<ProductsWithType>>

    @GET("wish-list/get-list")
    suspend fun getWishList(): Response<List<Product>>

    @GET("product/get-products-client")
    suspend fun getProducts(): Response<List<ProductsWithType>>

    @DELETE("wish-list/delete")
    suspend fun deleteWishItem(@Query("product_id") productId: Int): Response<WishList>

    @DELETE("wish-list/delete-in-products")
    suspend fun deleteWishItemInProducts(@Query("product_id") productId: Int): Response<List<ProductsWithType>>

    @POST("cart/add")
    suspend fun addCartItem(@Query("product_id") productId: Int, @Query("count") count: Int): Response<List<Product>>

    @GET("cart/get-list")
    suspend fun getCartList(): Response<List<Product>>

    @DELETE("cart/delete")
    suspend fun deleteCartItem(@Query("product_id") productId: Int): Response<WishList>

    @POST("order/add")
    suspend fun addOrder(): Response<List<Product>>

    @GET("order/get-list")
    suspend fun getOrders(): Response<List<Order>>

    @GET("order/{order_id}")
    suspend fun getOrder(@Path("order_id") orderId: Int): Response<Order>

    @PUT("order/cancel/")
    suspend fun cancelOrder(@Query("order_id") orderId: Int): Response<List<Order>>
}