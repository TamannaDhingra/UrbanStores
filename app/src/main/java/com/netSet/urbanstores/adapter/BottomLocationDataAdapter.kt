package com.netSet.urbanstores.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.R
import com.netSet.urbanstores.databinding.BottomLocationDataAdapterBinding
import com.netSet.urbanstores.models.BottomSheetLocationModel
import kotlinx.android.synthetic.main.bottom_location_data_adapter.view.*

class BottomLocationDataAdapter (var list: ArrayList<BottomSheetLocationModel>?): RecyclerView.Adapter<BottomLocationDataAdapter.ViewDataHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomLocationDataAdapter.ViewDataHolder {
        val binding= DataBindingUtil.inflate<BottomLocationDataAdapterBinding>(LayoutInflater.from(parent.context), R.layout.bottom_location_data_adapter,parent,false)
        return ViewDataHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewDataHolder, position: Int) {

        val data = list!![position]

        holder.itemView.apply {
            tvLocation.text=data.locationName

        }
    }
    override fun getItemCount(): Int {
        return list!!.size

    }
    inner class ViewDataHolder(binding:BottomLocationDataAdapterBinding): RecyclerView.ViewHolder(binding.root) {

    }
}