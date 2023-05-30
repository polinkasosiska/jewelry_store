package com.sysoliatina.jewelrystore.data.moderator

import com.sysoliatina.jewelrystore.models.*
import com.sysoliatina.jewelrystore.models.enums.OrderStatus
import retrofit2.Response
import retrofit2.http.*


interface ModeratorService {

    @POST("product-type/add")
    suspend fun addProductType(@Query("name") name: String): Response<List<ProductType>>

    @DELETE("product-type/{product_type_id}")
    suspend fun deleteProductType(@Path("product_type_id") productTypeId: Int): Response<Int>

    @POST("product/add")
    suspend fun addProduct(@Body request: AddProductReq): Response<List<ProductsWithType>>

    @PUT("product/{product_id}")
    suspend fun updateProduct(
        @Path("product_id") productId: Int,
        @Body request: AddProductReq
    ): Response<Product>

    @DELETE("product/{product_id}")
    suspend fun deleteProduct(@Path("product_id") productId: Int): Response<Int>

    @GET("order/all-orders")
    suspend fun getAllOrders(): Response<List<Order>>

    @GET("order/{order_id}")
    suspend fun getOrder(@Path("order_id") orderId: Int): Response<Order>

    @PUT("order/{order_id}")
    suspend fun updateOrderStatus(
        @Path("order_id") orderId: Int,
        @Query("order_status") status: OrderStatus
    ): Response<List<Order>>
}