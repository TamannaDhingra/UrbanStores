package com.netSet.urbanstores.ui.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.R
import com.netSet.urbanstores.databinding.CartViewBinding
import com.netSet.urbanstores.models.ShopProductsList


class VegitablesCartAdapter(
    val fragment: CartFrag,
    val vegeList: List<ShopProductsList>
) : RecyclerView.Adapter<VegitablesCartAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : CartViewBinding) : RecyclerView.ViewHolder(binding.root){
        val increaseI = binding.increaseItems
        val decreaseI = binding.decreaseItems
        val items = binding.itemCount
        val proName = binding.productName
        val proPrice = binding.productAmount
        val proDiscount = binding.productDiscount
        val proPcs = binding.quantitys
        val vegeImg = binding.productImg
        val productDiscount = binding.discountTxt
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<CartViewBinding>(LayoutInflater.from(parent.context),
            R.layout.cart_view,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Cart Pieces Increment Listener
        holder.increaseI.setOnClickListener {
            if (holder.items.text.toString().toInt()>0) {
                holder.proPcs.setText("" + (Integer.parseInt(holder.items.getText().toString()) + 1) +" Pcs")
                holder.items.setText("" + (Integer.parseInt(holder.items.getText().toString()) + 1))
            }
        }

        //Cart Pieces Decrement Listener
        holder.decreaseI.setOnClickListener {
            if (holder.items.text.toString().toInt()>0) {
                holder.proPcs.setText("" + (Integer.parseInt(holder.items.getText().toString()) - 1) +" Pcs")
                holder.items.setText("" + (Integer.parseInt(holder.items.getText().toString()) - 1))
            }
        }

        //values  Initialization
        holder.proName.text = vegeList.get(position).productname
        holder.proPrice.text =  "Rs "+vegeList.get(position).productPrice.toString()
        holder.proDiscount.text = "Rs "+vegeList.get(position).productPrice.toString()
        holder.vegeImg.setImageResource(vegeList.get(position).productImg)
        holder.productDiscount.text = vegeList.get(position).discount.toString()+"%\noff"
        holder.proPcs.text = (vegeList.get(position).productPcs+1).toString() + " Pcs"
        holder.items.text = (vegeList.get(position).productPcs+1).toString()

        //Total Price Logic
        val totalFruitPrice = vegeList.get(position).productPrice.toString().toInt()
        val totalFruitPcs = vegeList.get(position).productPcs.toString().toInt()+1
        val finalPrice = totalFruitPrice*totalFruitPcs

        //Discount Value Set in final
        fragment.getBaseActivity().totalDiscountAmount = fragment.getBaseActivity().totalDiscountAmount+vegeList.get(position).discount.toInt()

        //Set Total Price
        fragment.getBaseActivity().cartTotalAmount += finalPrice
        Log.e("thefinalAmountIs", fragment.getBaseActivity().cartTotalAmount.toString())
        fragment.setTotal()
    }

    override fun getItemCount(): Int {
        return vegeList.size
    }
}