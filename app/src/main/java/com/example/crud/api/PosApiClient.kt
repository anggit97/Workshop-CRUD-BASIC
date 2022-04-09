package com.example.crud.api

import com.example.crud.model.ClaimListResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

//Untuk mendefinisikn endpoint yang akan digunakan pada aplikasi
interface PosApiClient {

    @POST("showclaim_api")
    fun getClaimList(@Header("Authorization") authToken: String) : Call<ClaimListResponse>
}