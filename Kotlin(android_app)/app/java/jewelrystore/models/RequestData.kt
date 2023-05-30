package com.sysoliatina.jewelrystore.models

import com.google.gson.annotations.SerializedName


data class UserData(
    val email: String,
    @SerializedName("full_name") val fullName: String,
    val password: String
)

data class ClientData(val address: String, val phone: String)

data class SignUpReq(
    @SerializedName("user_data") val userData: UserData,
    @SerializedName("client_data") val clientData: ClientData
)

data class AddProductReq(
    @SerializedName("product_type_id") val productTypeId: Int,
    val name: String,
    val price: Int,
    @SerializedName("manufacture_date") val manufactureDate: String,
    val material: String,
    val color: String,
    val country: String,
    val weight: Int
)