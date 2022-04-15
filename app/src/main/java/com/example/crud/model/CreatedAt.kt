package com.example.crud.model


import com.google.gson.annotations.SerializedName

data class CreatedAt(
    @SerializedName("date")
    val date: String,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_type")
    val timezoneType: Int
)