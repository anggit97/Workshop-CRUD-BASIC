package com.example.crud.model


import com.google.gson.annotations.SerializedName

data class SearchClaimResponse(
    @SerializedName("data")
    val `data`: List<Claimahas>
)