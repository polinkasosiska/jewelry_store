package com.sysoliatina.jewelrystore.models

import com.google.gson.annotations.SerializedName
import com.sysoliatina.jewelrystore.models.enums.OrderStatus

data class Order(
    val id: Int,
    var status: OrderStatus,
    @SerializedName("user_email") val userEmail: String,
    @SerializedName("user_name") val userName: String,
    val products: List<Product>,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("order_date") val orderDate: String,
    @SerializedName("order_price") val orderPrice: Int,
)