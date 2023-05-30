package com.sysoliatina.jewelrystore.models

import com.google.gson.annotations.SerializedName

data class WishList(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("product_id") val productId: Int,
)
