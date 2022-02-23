package com.netSet.urbanstores.ui.orders.orderDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.R
import com.netSet.urbanstores.databinding.InflaterMyOrdersBinding
import com.netSet.urbanstores.databinding.InflaterTrackOrderBinding
import com.netSet.urbanstores.models.ModelClssMyOrders
import com.netSet.urbanstores.models.ModelClssTrackOrder
import com.netSet.urbanstores.ui.orders.myOrders.AdapterMyOrders

class AdapterOrderDetails(var arrayList123:ArrayList<ModelClssTrackOrder>, val context: Context): RecyclerView.Adapter
<AdapterOrderDetails.ViewHolderClass>(){
    class ViewHolderClass(val binding: InflaterTrackOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun trackOrder(String: ModelClssTrackOrder) {
            binding.trackOrder = String


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val binding=InflaterTrackOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
          return ViewHolderClass(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        holder.trackOrder(arrayList123.get(position))

//       when(position){
//           4->holder.binding.view1.visibility=View.GONE
//       }
      if(position==0 || position==1){
          holder.binding.orderePlacedDate.visibility = View.VISIBLE
          holder.binding.imageView.setImageResource(R.mipmap.check)

      }else{
          holder.binding.orderePlacedDate.visibility = View.GONE
          holder.binding.imageView.setImageResource(R.mipmap.uncheck)
      }

        if (position == arrayList123.size -1) {
            holder.binding.view1.visibility=View.GONE
        } else {
            holder.binding.view1.visibility=View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return arrayList123.size
    }
}