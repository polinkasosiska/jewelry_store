package com.sysoliatina.jewelrystore.models

import com.google.gson.annotations.SerializedName

data class PrivateUser(
    val id: Int,
    @SerializedName("full_name") val fullName: String,
    val email: String,
    @SerializedName("is_moderator") val isModerator: Boolean,
    @SerializedName("access_token") val token: String
)