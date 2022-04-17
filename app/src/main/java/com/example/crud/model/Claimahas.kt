package com.example.crud.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Claimahas(
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
    val leadTimeStatusClaim: String?,
    @SerializedName("lead_time_status_closing")
    val leadTimeStatusClosing: String?,
    @SerializedName("leadtime_status_request")
    val leadtimeStatusRequest: String?,
    @SerializedName("part_number")
    val partNumber: String,
    @SerializedName("qty")
    val qty: Int,
    @SerializedName("status_claim")
    val statusClaim: String?,
    @SerializedName("status_closing")
    val statusClosing: String?,
    @SerializedName("status_request")
    val statusRequest: String?,
    @SerializedName("tgl_claim")
    val tglClaim: String,
    @SerializedName("tgl_status_status_claim")
    val tglStatusStatusClaim: String?,
    @SerializedName("tgl_status_status_closing")
    val tglStatusStatusClosing: String?,
    @SerializedName("tgl_status_status_request")
    val tglStatusStatusRequest: String?
) : Parcelable {

    override fun toString(): String {
        return partNumber
    }
}