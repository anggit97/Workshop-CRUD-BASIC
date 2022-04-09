package com.example.crud.model


import com.google.gson.annotations.SerializedName

data class DataDeleteClaim(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("created_by")
    val createdBy: String,
    @SerializedName("customer")
    val customer: String,
    @SerializedName("customer_name")
    val customerName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("foto")
    val foto: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("keterangan")
    val keterangan: String,
    @SerializedName("lead_time_status_claim")
    val leadTimeStatusClaim: Int,
    @SerializedName("lead_time_status_closing")
    val leadTimeStatusClosing: Int,
    @SerializedName("leadtime_status_request")
    val leadtimeStatusRequest: Int,
    @SerializedName("part_number")
    val partNumber: String,
    @SerializedName("qty")
    val qty: Int,
    @SerializedName("status_claim")
    val statusClaim: String,
    @SerializedName("status_closing")
    val statusClosing: String,
    @SerializedName("status_request")
    val statusRequest: String,
    @SerializedName("tgl_claim")
    val tglClaim: String,
    @SerializedName("tgl_status_status_claim")
    val tglStatusStatusClaim: String,
    @SerializedName("tgl_status_status_closing")
    val tglStatusStatusClosing: String,
    @SerializedName("tgl_status_status_request")
    val tglStatusStatusRequest: String
)