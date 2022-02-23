package com.netSet.urbanstores.ui.shops.ShopProducts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.R
import com.netSet.urbanstores.databinding.ShopproductsViewBinding

class ShopDiscountsAdapters : RecyclerView.Adapter<ShopDiscountsAdapters.ViewHolder>() {

    inner class ViewHolder(val binding : ShopproductsViewBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= DataBindingUtil.inflate<ShopproductsViewBinding>(LayoutInflater.from(parent.context),
            R.layout.shopproducts_view,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    override fun getItemCount(): Int {
        return 3
    }
}