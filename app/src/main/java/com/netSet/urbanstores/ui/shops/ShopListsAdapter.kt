package com.netSet.urbanstores.ui.shops

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.databinding.ShopsViewBinding
import com.netSet.urbanstores.ui.shops.ShopProducts.ShopProductsFrag

class ShopListsAdapter(val fragment: ShopsFragment) : RecyclerView.Adapter<ShopListsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : ShopsViewBinding ) : RecyclerView.ViewHolder( binding.root){
        val openProducts = binding.openProducts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ShopsViewBinding>(LayoutInflater.from(parent.context),
            R.layout.shops_view,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.openProducts.setOnClickListener {
           fragment.fragmentManager?.beginTransaction()?.replace(R.id.mainContainer,ShopProductsFrag())?.addToBackStack("")?.commit()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}