package com.netSet.urbanstores.ui.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.R
import com.netSet.urbanstores.databinding.CartViewBinding
import com.netSet.urbanstores.models.ShopProductsList

class PackageCartAdapter(var cartFrag: CartFrag,var packageList: List<ShopProductsList>) : RecyclerView.Adapter<PackageCartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PackageCartAdapter.ViewHolder {
        val view = DataBindingUtil.inflate< CartViewBinding>(LayoutInflater.from(parent.context), R.layout.cart_view,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PackageCartAdapter.ViewHolder, position: Int) {

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
        holder.proName.text = packageList.get(position).productname
        holder.proPrice.text =  "Rs "+packageList.get(position).productPrice.toString()
        holder.proDiscount.text = "Rs "+packageList.get(position).productPrice.toString()
        holder.vegeImg.setImageResource(packageList.get(position).productImg)
        if (packageList.get(position).discount.toString().toInt() ==0){
            holder.binding?.discountTxt?.visibility = View.GONE
        }else{
            holder.productDiscount.text = packageList.get(position).discount.toString() + "off"
        }

        holder.proPcs.text = (packageList.get(position).productPcs+1).toString() + " Pcs"
        holder.items.text = (packageList.get(position).productPcs+1).toString()

        //Total Price Logic
        val totalFruitPrice = packageList.get(position).productPrice.toString().toInt()
        val totalFruitPcs = packageList.get(position).productPcs.toString().toInt()+1
        val finalPrice = totalFruitPrice*totalFruitPcs

        //Discount Value Set in final
        cartFrag.getBaseActivity().totalDiscountAmount = cartFrag.getBaseActivity().totalDiscountAmount+packageList.get(position).discount

        //Set Total Price
        cartFrag.getBaseActivity().cartTotalAmount += finalPrice
        Log.e("thefinalAmountIs", cartFrag.getBaseActivity().cartTotalAmount.toString())
        cartFrag.setTotal()
    }

    override fun getItemCount(): Int {
        return packageList.size
    }

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
}