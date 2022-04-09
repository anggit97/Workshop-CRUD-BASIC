package com.example.crud.model


import com.google.gson.annotations.SerializedName

data class ClaimDeleteResponse(
    @SerializedName("data")
    val dataDeleteClaim: DataDeleteClaim,
    @SerializedName("message")
    val message: String
)