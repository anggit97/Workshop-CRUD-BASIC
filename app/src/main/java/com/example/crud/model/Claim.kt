package com.example.crud.model


import com.google.gson.annotations.SerializedName

data class Claim(
    @SerializedName("created_at")
    val createdAt: String
)