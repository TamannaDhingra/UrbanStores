package com.netSet.urbanstores.ui.shops.ShopProducts.allProducts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseActivity
import com.netSet.urbanstores.databinding.AllproductsViewBinding
import com.netSet.urbanstores.models.AllProductsModel

class AllproductsAdapter(
    val allProductsFrag: AllProductsFrag,
    val productsList: ArrayList<AllProductsModel>,
    val baseActivity: BaseActivity
) : RecyclerView.Adapter<AllproductsAdapter.viewHolder>(){

    var quantityList = arrayListOf("1Pcs","2Pcs","3Pcs","4Pcs","5Pcs","6Pcs","7Pcs","8Pcs","9Pcs","10Pcs")

    inner class viewHolder(val binding : AllproductsViewBinding) : RecyclerView.ViewHolder(binding.root){
        val proName = binding.productName
        val proPrice = binding.productAmount
        val proDiscount = binding.productDiscount
        val addBtn = binding.itemAddButton
        val removeBtn = binding.itemRemoveButton
        val discount = binding.discountTxt
        val noPcs = binding.quantitys
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = DataBindingUtil.inflate<AllproductsViewBinding>(LayoutInflater.from(parent.context),
            R.layout.allproducts_view,parent,false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        //Quantity Spinner Adapter
            val adapter = ArrayAdapter(allProductsFrag.requireContext(), R.layout.quantity_spinner_layout,quantityList)
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            holder.binding.quantitys.adapter = adapter

        holder.proName.text = productsList.get(position).productname
        holder.proPrice.text = productsList.get(position).productPrice.toString() +" Rs."
        holder.proDiscount.text = productsList.get(position).discount
        holder.discount.text =productsList.get(position).discount + "% \n off"
        holder.noPcs.setSelection(productsList.get(position).productPcs)

        if (productsList.get(position).isAddedToCart){
            holder.removeBtn.visibility = View.VISIBLE
            holder.addBtn.visibility = View.GONE
        }else{
            holder.removeBtn.visibility = View.GONE
            holder.addBtn.visibility = View.VISIBLE
        }

        holder.addBtn.setOnClickListener {
            baseActivity.cartCallback?.addtocart(position,  holder.noPcs.selectedItemPosition)
        }

        holder.removeBtn.setOnClickListener {
            baseActivity.cartCallback?.removefromcart(position)
        }

    }

    override fun getItemCount(): Int {
        return productsList.size
    }


}