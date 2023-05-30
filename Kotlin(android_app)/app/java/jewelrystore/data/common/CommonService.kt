package com.sysoliatina.jewelrystore.data.common

import com.sysoliatina.jewelrystore.models.*
import retrofit2.Response
import retrofit2.http.*


interface CommonService {

    @POST("auth/sign-in")
    @Headers("accept: application/json", "Content-Type: application/x-www-form-urlencoded")
    suspend fun auth(@Body request: String): Response<PrivateUser>

    @POST("auth/sign-up")
    suspend fun signUp(@Body request: SignUpReq): Response<PrivateUser>

    @GET("product-type/get-list")
    suspend fun getProductTypes(): Response<List<ProductType>>

    @GET("product/get-list")
    suspend fun getProducts(): Response<List<ProductsWithType>>

    @GET("product/{product_id}")
    suspend fun getProduct(@Path("product_id") productId: Int): Response<Product>
}