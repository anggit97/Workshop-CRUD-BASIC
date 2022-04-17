package com.example.crud.adapter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import com.example.crud.model.Claimahas

class AutocompleteClaimAdapter(context: Context, resource: Int) : ArrayAdapter<Claimahas>(context, resource), Filterable {
    private val mlistData: MutableList<Claimahas>

    fun setData(list: List<Claimahas>?) {
        mlistData.clear()
        mlistData.addAll(list!!)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mlistData.size
    }

    override fun getItem(position: Int): Claimahas {
        return mlistData[position]
    }

    /**
     * Used to Return the full object directly from adapter.
     *
     * @param position
     * @return
     */
    fun getObject(position: Int): Claimahas {
        return mlistData[position]
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    filterResults.values = mlistData
                    filterResults.count = mlistData.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }

    init {
        mlistData = ArrayList()
    }
}