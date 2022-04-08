package com.netSet.urbanstores.ui.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseActivity
import com.netSet.urbanstores.databinding.CartViewBinding
import com.netSet.urbanstores.models.AllProductsModel
import com.netSet.urbanstores.models.ShopProductsList
import com.netSet.urbanstores.room.CartEntity
import kotlin.properties.Delegates

class FruitsCartAdapter(
    var fragment: CartFrag,
    var productsList: List<CartEntity>?
) : RecyclerView.Adapter<FruitsCartAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : CartViewBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<CartViewBinding>(LayoutInflater.from(parent.context),
            R.layout.cart_view,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem= productsList?.get(position)
        holder.binding.productName.text=currentItem?.productname.toString()
        holder.binding.productAmount.text=currentItem?.productPrice.toString()
        holder.binding.quantitys.setText(currentItem?.productsQuantity.toString() + "Pcs")

        Log.e("datais", productsList?.get(position)?.isAddedToCart.toString())
        fragment.setImageUsingGlide(
            fragment,
            productsList?.get(position)?.image_url.toString(),
            holder.binding.productImg
        )

        holder.binding.itemCount.setText(currentItem?.productsQuantity.toString() )
        /** Cart Pieces Increment Listener*/
        holder.binding.increaseItems.setOnClickListener {

            fragment.increaseItemCount(position)
        }
        /**  Cart Pieces Decrement Listener */
        holder.binding.decreaseItems.setOnClickListener {

            fragment.decreaseItemCount(position)

        }


        //Total Price Logic
        val totalFruitPrice = productsList?.get(position)?.productPrice?.toFloat()
        val totalFruitPcs = productsList?.get(position)?.productsQuantity?.toInt()
        val finalPrice = (totalFruitPrice?.times(totalFruitPcs!!)).toString()

        //   val finalPrice = (totalFruitPrice*(totalFruitPcs)).toString()


        //Set Total Price
        fragment.getBaseActivity().cartTotalAmount =  fragment.getBaseActivity().cartTotalAmount + finalPrice.toFloat()
        Log.e("thefinalAmountIs", fragment.getBaseActivity().cartTotalAmount.toString())
        fragment.setTotal()


    }


    override fun getItemCount(): Int {
        return productsList?.size!!
    }
}