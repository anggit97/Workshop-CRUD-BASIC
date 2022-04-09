package com.example.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.adapter.ClaimAdapter
import com.example.crud.api.PosApiClient
import com.example.crud.api.PosServiceGenerator
import com.example.crud.model.ClaimListResponse
import com.example.crud.model.Claimahas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClaimActivity : AppCompatActivity() {

//    private var claimList = mutableListOf<Claim>()

    private lateinit var adapter: ClaimAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_claim)

        val rvClaim = findViewById<RecyclerView>(R.id.rvList)

        adapter = ClaimAdapter()

        rvClaim.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvClaim.adapter = adapter

        getDataClaim()
    }

    private fun getDataClaim() {
        val client: PosApiClient =
            PosServiceGenerator.createService(PosApiClient::class.java)
        client.getClaimList("bearer ${AppConstant.TOKEN_JWT}").enqueue(object : Callback<ClaimListResponse> {
            override fun onResponse(
                call: Call<ClaimListResponse>,
                response: Response<ClaimListResponse>
            ) {
                if (response.isSuccessful) {
                    adapter.setItems(response.body()?.claimahass?.toMutableList() ?: emptyList<Claimahas>() as MutableList<Claimahas>)
                } else {
                    Toast.makeText(this@ClaimActivity, "Terjadi kesalahan Response", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "onFailure: Terjadi Kesalahan")
                }
            }

            override fun onFailure(call: Call<ClaimListResponse>, t: Throwable) {
                Toast.makeText(this@ClaimActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}", t)
            }
        })
    }

    companion object {
        const val TAG = "ClaimActivity"
    }
}