package com.example.crud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crud.databinding.RowItemListBinding
import com.example.crud.model.Claimahas

class ClaimAdapter : RecyclerView.Adapter<ClaimAdapter.ViewHolder>() {

    private var claimList = mutableListOf<Claimahas>()
    private lateinit var listener: ClaimAdapterClickListener

    fun setItems(dataList: MutableList<Claimahas>) {
        claimList = dataList
        notifyDataSetChanged()
    }

    fun setClickListener(listener: ClaimAdapterClickListener) {
        this.listener = listener
    }

    class ViewHolder(private val view: RowItemListBinding, private val listener: ClaimAdapterClickListener) :
        RecyclerView.ViewHolder(view.root) {
        fun bindItem(item: Claimahas) {
            view.tvContentCustomerName.text = item.customerName
            view.tvContentPartNumber.text = item.partNumber

            Glide.with(view.root.context)
                .load(
                    item.foto
                        ?: "https://upload.wikimedia.org/wikipedia/commons/6/66/Android_robot.png"
                )
                .into(view.ivImage)

            view.root.setOnLongClickListener {
                listener.onClickDelete(item.id.toString())
                return@setOnLongClickListener true
            }
        }
    }

    //Mendefiniskan layout apa yang kita gunakan di adapter ini
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClaimAdapter.ViewHolder {
        val view = RowItemListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view, listener)
    }

    //Untuk menempelkan data ke view
    override fun onBindViewHolder(holder: ClaimAdapter.ViewHolder, position: Int) {
        val item = claimList[position]
        holder.bindItem(item)
    }

    //Menentukan ada berapa banyak jumlah item di list
    override fun getItemCount(): Int = claimList.size
}

interface ClaimAdapterClickListener {
    fun onClickDelete(claimId: String)
}
