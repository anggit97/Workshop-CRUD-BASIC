package com.example.crud.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.crud.AppConstant
import com.example.crud.R
import com.example.crud.api.PosApiClient
import com.example.crud.api.PosServiceGenerator
import com.example.crud.model.ClaimCreateRequest
import com.example.crud.model.ClaimCreateResponse
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateUpdateActivity : AppCompatActivity() {

    private lateinit var customer: TextInputEditText
    private lateinit var customerName: TextInputEditText
    private lateinit var partNumber: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var qty: TextInputEditText
    private lateinit var claimDate: TextInputEditText
    private lateinit var note: TextInputEditText
    private lateinit var btnSave: Button
    private lateinit var pbLoading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_update)

        customer = findViewById(R.id.etCustomer)
        customerName = findViewById(R.id.etCustomerName)
        partNumber = findViewById(R.id.etPartNumber)
        description = findViewById(R.id.etDescription)
        qty = findViewById(R.id.etQty)
        claimDate = findViewById(R.id.etClaimDate)
        note = findViewById(R.id.etNote)
        btnSave = findViewById(R.id.btnSave)
        pbLoading = findViewById(R.id.pbLoading)


        btnSave.setOnClickListener {
            val customerValue = customer.text.toString()
            val customerNameValue = customerName.text.toString()
            val partNumberValue = partNumber.text.toString()
            val descriptionValue = description.text.toString()
            val qtyValue = qty.text.toString()
            val claimDateValue = claimDate.text.toString()
            val noteValue = note.text.toString()

            var valid = true
            if (customerValue.isEmpty() || customerValue.isBlank()) {
                customer.error = "Customer Harus diisi"
                valid = false
            }

            if (customerNameValue.isEmpty() || customerNameValue.isBlank()) {
                customerName.error = "Customer Name Harus diisi"
                valid = false
            }

            if (partNumberValue.isEmpty() || partNumberValue.isBlank()) {
                partNumber.error = "Part number harus diisi"
                valid = false
            }

            if (descriptionValue.isEmpty() || descriptionValue.isBlank()) {
                description.error = "Part Number harus diisi"
                valid = false
            }

            if (qtyValue.isEmpty() || qtyValue.isBlank()) {
                qty.error = "QTY harus diisi"
                valid = false
            } else {
                if (qtyValue.toInt() <= 0) {
                    qty.error = "Qty harus lebih dari 0"
                    valid = false
                }
            }

            if (claimDateValue.isEmpty() || claimDateValue.isBlank()) {
                claimDate.error = "claim Date harus diisi"
                valid = false
            }

            if (noteValue.isEmpty() || noteValue.isBlank()) {
                note.error = "note harus diisi"
                valid = false
            }

            if (valid) {
                val request = ClaimCreateRequest(
                    customerValue,
                    customerNameValue,
                    descriptionValue,
                    noteValue,
                    partNumberValue,
                    qtyValue.toInt(),
                    claimDateValue
                )

                createClaim(request)
            }
        }
    }

    private fun createClaim(request: ClaimCreateRequest) {
        pbLoading.visibility = View.VISIBLE
        val client: PosApiClient = PosServiceGenerator.createService(PosApiClient::class.java)
        client.createClaim(AppConstant.TOKEN_JWT, request)
            .enqueue(object : Callback<ClaimCreateResponse> {
                override fun onResponse(
                    call: Call<ClaimCreateResponse>,
                    response: Response<ClaimCreateResponse>
                ) {
                    pbLoading.visibility = View.GONE
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@CreateUpdateActivity,
                            "Berhasil buat claim",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@CreateUpdateActivity,
                            "Terjadi Kesalahan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ClaimCreateResponse>, t: Throwable) {
                    pbLoading.visibility = View.GONE
                    Log.e(TAG, "onFailure: ", t)
                    Toast.makeText(
                        this@CreateUpdateActivity,
                        "Terjadi Kesalahan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    companion object {
        const val TAG = "CreateUpdateActivity"
    }
}