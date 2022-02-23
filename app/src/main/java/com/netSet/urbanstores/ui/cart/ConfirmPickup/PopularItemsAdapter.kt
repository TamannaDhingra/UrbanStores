package com.netSet.urbanstores.ui.cart.ConfirmPickup

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.R
import com.netSet.urbanstores.databinding.PopularItemsViewBinding

class PopularItemsAdapter(val fragment: OrderConfirmFrag) : RecyclerView.Adapter<PopularItemsAdapter.ViewHolder>() {

    var quantityList = arrayListOf("1Pcs","2Pcs","3Pcs","4Pcs","5Pcs","6Pcs","7Pcs","8Pcs","9Pcs","10Pcs")

    inner class ViewHolder(var binding : PopularItemsViewBinding) : RecyclerView.ViewHolder(binding.root){
        val noPcs = binding.quantitys
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<PopularItemsViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.popular_items_view,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val adapter = ArrayAdapter(fragment.requireContext(), R.layout.quantity_spinner_layout,quantityList)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        holder.noPcs.adapter = adapter
    }

    override fun getItemCount(): Int {
        return 5
    }
}