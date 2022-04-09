package com.example.crud

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.adapter.ClaimAdapter
import com.example.crud.adapter.ClaimAdapterClickListener
import com.example.crud.api.PosApiClient
import com.example.crud.api.PosServiceGenerator
import com.example.crud.model.ClaimDeleteResponse
import com.example.crud.model.ClaimListResponse
import com.example.crud.model.Claimahas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClaimActivity : AppCompatActivity(), ClaimAdapterClickListener {

//    private var claimList = mutableListOf<Claim>()

    private lateinit var adapter: ClaimAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_claim)

        val rvClaim = findViewById<RecyclerView>(R.id.rvList)

        adapter = ClaimAdapter()
        adapter.setClickListener(this)

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

    override fun onClickDelete(claimId: String) {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)

        // set title dialog
        alertDialogBuilder.setTitle("Apakah anda yakin ingin menghapus item ini?")

        // set pesan dari dialog
        alertDialogBuilder
            .setMessage("Klik Ya untuk keluar!")
            .setCancelable(false)
            .setPositiveButton(
                "Hapus"
            ) { dialog, id -> // jika tombol diklik, maka akan menutup activity ini
                deleteClaim(claimId)
            }
            .setNegativeButton(
                "Batal",
                DialogInterface.OnClickListener { dialog, id -> // jika tombol ini diklik, akan menutup dialog
                    // dan tidak terjadi apa2
                    dialog.cancel()
                })

        // membuat alert dialog dari builder
        val alertDialog: AlertDialog = alertDialogBuilder.create()

        // menampilkan alert dialog
        alertDialog.show()
    }

    private fun deleteClaim(claimId: String){
        val client: PosApiClient =
            PosServiceGenerator.createService(PosApiClient::class.java)
        client.deleteClaim("bearer ${AppConstant.TOKEN_JWT}", claimId).enqueue(object : Callback<ClaimDeleteResponse>{
            override fun onResponse(
                call: Call<ClaimDeleteResponse>,
                response: Response<ClaimDeleteResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ClaimActivity, "Berhasil hapus claim", Toast.LENGTH_SHORT).show()
                    getDataClaim()
                } else {
                    Toast.makeText(this@ClaimActivity, "Gagal hapus claim", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "onFailure: Terjadi Kesalahan")
                }
            }

            override fun onFailure(call: Call<ClaimDeleteResponse>, t: Throwable) {
                Toast.makeText(this@ClaimActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}", t)
            }
        })
    }
}