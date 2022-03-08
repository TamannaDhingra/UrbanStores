package com.netSet.urbanstores.ui.orders.myOrders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseActivity
import com.netSet.urbanstores.databinding.InflaterMyOrdersBinding
import com.netSet.urbanstores.models.ModelClssMyOrders
import com.netSet.urbanstores.ui.cart.ConfirmPickup.OrderConfirmFrag
import com.netSet.urbanstores.ui.orders.orderDetails.FragmentOrderDetails

class AdapterMyOrders(var arrayList123: ArrayList<ModelClssMyOrders>,val baseActivity: BaseActivity): RecyclerView.Adapter
<AdapterMyOrders.ViewHolderClass>() {

    inner class ViewHolderClass(val binding: InflaterMyOrdersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun orderDetails(String: ModelClssMyOrders) {
            binding.orderDetails = String

            binding.tvRepeatOrder.setOnClickListener {
                baseActivity.replaceFragment(OrderConfirmFrag(), true, false,)
            }

            binding.cardView.setOnClickListener {
                baseActivity.replaceFragment(FragmentOrderDetails(), true, false,)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val binding = InflaterMyOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderClass(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        holder.orderDetails(arrayList123.get(position))

        if(position==0){
            holder.binding.tvRepeatOrder.visibility=View.GONE
            holder.binding.tvOrderInProgress.setText("Order In Progress")
            holder.binding.tvOrderInProgress.setTextColor(Color.parseColor("#FF7C00"))
        }
        else{
            holder.binding.tvRepeatOrder.visibility=View.VISIBLE
            holder.binding.tvOrderInProgress.setText("Order Delivered")
            holder.binding.tvOrderInProgress.setTextColor(Color.parseColor("#37d176"))
        }

        if(position==1){
            holder.binding.textViewHeading.setText("Completed")
            (holder.binding.textViewHeading.layoutParams as ConstraintLayout.LayoutParams).apply {
                marginStart=0
                topMargin=30
                marginEnd=0
                bottomMargin=15
            }
        }
        if(position==0 || position==1){
            holder.binding.textViewHeading.visibility= View.VISIBLE
        }else{
            holder.binding.textViewHeading.visibility=View.GONE

        }

    }

    override fun getItemCount(): Int {
        return arrayList123.size
    }
}