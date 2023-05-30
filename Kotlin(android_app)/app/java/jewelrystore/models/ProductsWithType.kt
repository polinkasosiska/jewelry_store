package com.sysoliatina.jewelrystore.models

import com.google.gson.annotations.SerializedName

data class ProductsWithType(
    @SerializedName("product_type_id") val productTypeId: Int,
    @SerializedName("product_type") val productType: String,
    val products: List<Product>
)
