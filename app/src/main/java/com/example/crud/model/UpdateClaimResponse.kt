package com.example.crud.model


import com.google.gson.annotations.SerializedName

data class UpdateClaimResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)