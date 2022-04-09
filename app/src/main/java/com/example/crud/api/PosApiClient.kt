package com.example.crud.api

import com.example.crud.model.ClaimDeleteResponse
import com.example.crud.model.ClaimListResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

//Untuk mendefinisikn endpoint yang akan digunakan pada aplikasi
interface PosApiClient {

    @POST("showclaim_api")
    fun getClaimList(@Header("Authorization") authToken: String) : Call<ClaimListResponse>

    @DELETE("deleteclaim_api/{claimId}")
    fun deleteClaim(@Header("Authorization") authToken: String, @Path("claimId") claimId: String) : Call<ClaimDeleteResponse>
}