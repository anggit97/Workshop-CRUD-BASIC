package com.example.crud.model


import com.google.gson.annotations.SerializedName

data class ClaimListResponse(
    @SerializedName("claim")
    val claim: List<Claim>,
    @SerializedName("claimahass")
    val claimahass: List<Claimahas>
)