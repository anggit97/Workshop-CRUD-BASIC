package com.example.crud.api

import com.example.crud.model.ClaimCreateRequest
import com.example.crud.model.ClaimCreateResponse
import com.example.crud.model.ClaimDeleteResponse
import com.example.crud.model.ClaimListResponse
import retrofit2.Call
import retrofit2.http.*

//Untuk mendefinisikn endpoint yang akan digunakan pada aplikasi
interface PosApiClient {

    @GET("showclaim_api")
    fun getClaimList(@Header("Authorization") authToken: String) : Call<ClaimListResponse>

    @DELETE("deleteclaim_api/{claimId}")
    fun deleteClaim(@Header("Authorization") authToken: String, @Path("claimId") claimId: String) : Call<ClaimDeleteResponse>

    @POST("createclaim_api")
    fun createClaim(@Header("Authorization") authToken: String, @Body claimRequest: ClaimCreateRequest) : Call<ClaimCreateResponse>
}