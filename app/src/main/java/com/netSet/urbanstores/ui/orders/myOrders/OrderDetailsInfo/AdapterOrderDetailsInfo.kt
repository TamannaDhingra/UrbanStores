package com.netSet.urbanstores.ui.orders.OrderDetailsInfo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.databinding.InflaterItemsDetailsBinding
import com.netSet.urbanstores.databinding.InflaterTrackOrderBinding
import com.netSet.urbanstores.models.ModelClassItems
import com.netSet.urbanstores.models.ModelClssTrackOrder
import com.netSet.urbanstores.ui.orders.orderDetails.AdapterOrderDetails

class AdapterOrderDetailsInfo(var arrayList123:ArrayList<ModelClassItems>, val context: Context): RecyclerView.Adapter
<AdapterOrderDetailsInfo.ViewHolderClass>() {

    class ViewHolderClass(val binding: InflaterItemsDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun itemListDetail(String: ModelClassItems) {
            binding.itemList = String


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val binding=InflaterItemsDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderClass(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        holder.itemListDetail(arrayList123.get(position))


    }

    override fun getItemCount(): Int {
      return arrayList123.size
    }

}