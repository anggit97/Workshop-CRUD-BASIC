package com.example.crud.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.AppConstant
import com.example.crud.R
import com.example.crud.adapter.ClaimAdapter
import com.example.crud.adapter.ClaimAdapterClickListener
import com.example.crud.api.PosApiClient
import com.example.crud.api.PosServiceGenerator
import com.example.crud.model.ClaimDeleteResponse
import com.example.crud.model.ClaimListResponse
import com.example.crud.model.Claimahas
import com.example.crud.model.SearchClaimResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ClaimActivity : AppCompatActivity(), ClaimAdapterClickListener {

    private lateinit var adapter: ClaimAdapter
    private var keyword = ""

    private lateinit var pbLoading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_claim)

        val rvClaim = findViewById<RecyclerView>(R.id.rvList)
        val fabCreate = findViewById<FloatingActionButton>(R.id.fabCreate)
        val search = findViewById<TextInputEditText>(R.id.etSearch)
        val ivAutocompleteClaim = findViewById<ImageView>(R.id.ivAutocomplete)
        pbLoading = findViewById(R.id.pbLoading)

        adapter = ClaimAdapter()
        adapter.setClickListener(this)

        rvClaim.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvClaim.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rvClaim.adapter = adapter

        ivAutocompleteClaim.setOnClickListener {
            val intent = Intent(this, AutocompleteClaimActivity::class.java)
            startActivity(intent)
        }

        fabCreate.setOnClickListener {
            val intent = Intent(this, CreateUpdateActivity::class.java)
            startActivity(intent)
        }

        search.addTextChangedListener(object: TextWatcher{

            private var timer = Timer()
            private val DELAY = 1000L //millisecond

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                if (editable != null){
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(object : TimerTask(){
                        override fun run() {
                            keyword = editable.toString()
                            getDataClaim()
                        }
                    }, DELAY)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getDataClaim()
    }

    private fun getDataClaim() {
        runOnUiThread { pbLoading.visibility = View.VISIBLE }
        val client: PosApiClient =
            PosServiceGenerator.createService(PosApiClient::class.java)
        client.searchClaimList(AppConstant.TOKEN_JWT, keyword).enqueue(object : Callback<SearchClaimResponse> {
            override fun onResponse(
                call: Call<SearchClaimResponse>,
                response: Response<SearchClaimResponse>
            ) {
                pbLoading.visibility = View.GONE
                if (response.isSuccessful) {
                    adapter.setItems(response.body()?.data?.toMutableList() ?: emptyList<Claimahas>() as MutableList<Claimahas>)
                } else {
                    Toast.makeText(this@ClaimActivity, "Terjadi kesalahan Response", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "onFailure: Terjadi Kesalahan")
                }
            }

            override fun onFailure(call: Call<SearchClaimResponse>, t: Throwable) {
                pbLoading.visibility = View.GONE
                Toast.makeText(this@ClaimActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}", t)
            }
        })
    }

    companion object {
        const val TAG = "ClaimActivity"
    }

    override fun onClickItem(claim: Claimahas) {
        val intent = Intent(this, CreateUpdateActivity::class.java)
        intent.putExtra(AppConstant.ITEM_KEY, claim)
        startActivity(intent)
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
