package com.example.crud.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.example.crud.AppConstant
import com.example.crud.R
import com.example.crud.adapter.AutocompleteClaimAdapter
import com.example.crud.api.PosApiClient
import com.example.crud.api.PosServiceGenerator
import com.example.crud.model.Claimahas
import com.example.crud.model.SearchClaimResponse
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AutocompleteClaimActivity : AppCompatActivity() {

    private lateinit var customer: TextInputEditText
    private lateinit var customerName: TextInputEditText
    private lateinit var partNumber: AutoCompleteTextView
    private lateinit var description: TextInputEditText
    private lateinit var qty: TextInputEditText
    private lateinit var claimDate: TextInputEditText
    private lateinit var note: TextInputEditText
    private lateinit var btnSave: Button
    private lateinit var pbLoading: ProgressBar
    private lateinit var ivBack: ImageView

    private var keyword = ""

    private lateinit var autoCompleteAdapter : AutocompleteClaimAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autocomplete_claim)

        customer = findViewById(R.id.etCustomer)
        customerName = findViewById(R.id.etCustomerName)
        partNumber = findViewById(R.id.etPartNumber)
        description = findViewById(R.id.etDescription)
        qty = findViewById(R.id.etQty)
        claimDate = findViewById(R.id.etClaimDate)
        note = findViewById(R.id.etNote)
        btnSave = findViewById(R.id.btnSave)
        pbLoading = findViewById(R.id.pbLoading)
        ivBack = findViewById(R.id.ivBack)

        ivBack.setOnClickListener {
            finish()
        }

        autoCompleteAdapter = AutocompleteClaimAdapter(this, android.R.layout.simple_dropdown_item_1line)
        partNumber.threshold = 2
        partNumber.setAdapter(autoCompleteAdapter)

        partNumber.setOnItemClickListener { adapterView, view, i, l ->
            val data = autoCompleteAdapter.getObject(i)
            customer.setText(data.customer)
            customerName.setText(data.customerName)
            description.setText(data.description)
            qty.setText(data.qty.toString())
            claimDate.setText(data.tglClaim)
            note.setText(data.keterangan)
        }

        partNumber.addTextChangedListener(object: TextWatcher{
            private var timer = Timer()
            private val DELAY = 1000L //millisecond
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(edittable: Editable?) {
                if (edittable != null){
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(object : TimerTask(){
                        override fun run() {
                            keyword = edittable.toString()
                            getDataClaim()
                        }
                    }, DELAY)
                }
            }
        })
    }

    private fun getDataClaim() {
        runOnUiThread { pbLoading.visibility = View.VISIBLE }
        val client: PosApiClient =
            PosServiceGenerator.createService(PosApiClient::class.java)
        client.searchClaimList(AppConstant.TOKEN_JWT, keyword).enqueue(object :
            Callback<SearchClaimResponse> {
            override fun onResponse(
                call: Call<SearchClaimResponse>,
                response: Response<SearchClaimResponse>
            ) {
                pbLoading.visibility = View.GONE
                if (response.isSuccessful) {
                    autoCompleteAdapter.setData(response.body()?.data)
                } else {
                    Toast.makeText(this@AutocompleteClaimActivity, "Terjadi kesalahan Response", Toast.LENGTH_SHORT).show()
                    Log.e(ClaimActivity.TAG, "onFailure: Terjadi Kesalahan")
                }
            }

            override fun onFailure(call: Call<SearchClaimResponse>, t: Throwable) {
                pbLoading.visibility = View.GONE
                Toast.makeText(this@AutocompleteClaimActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                Log.e(ClaimActivity.TAG, "onFailure: ${t.message}", t)
            }
        })
    }
}