package com.sysoliatina.jewelrystore.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    @SerializedName("product_type_id") val productTypeId: Int,
    val name: String,
    val price: Int,
    @SerializedName("manufacture_date") val manufactureDate: String,
    val material: String,
    val color: String,
    val country: String,
    val weight: Int,
    val count: Int?,
    @SerializedName("is_wished") val isWished: Boolean?
) : Parcelable
