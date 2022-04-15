package com.example.crud.model


import com.google.gson.annotations.SerializedName

data class ClaimCreateRequest(
    @SerializedName("customer")
    val customer: String,
    @SerializedName("customer_name")
    val customerName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("keterangan")
    val keterangan: String,
    @SerializedName("part_number")
    val partNumber: String,
    @SerializedName("qty")
    val qty: Int,
    @SerializedName("tgl_claim")
    val tglClaim: String
)