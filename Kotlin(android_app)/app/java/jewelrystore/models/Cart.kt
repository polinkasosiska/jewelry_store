package com.sysoliatina.jewelrystore.models

import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("product_id") val productId: Int,
    val count: Int
)
