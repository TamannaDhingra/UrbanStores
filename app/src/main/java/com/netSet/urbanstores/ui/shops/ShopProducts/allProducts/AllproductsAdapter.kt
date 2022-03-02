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
import com.netSet.urbanstores.models.ShopProductsList

class AllproductsAdapter(
    val allProductsFrag: AllProductsFrag,
    val shopProductsList: ArrayList<ShopProductsList>,
    val baseActivity: BaseActivity
) : RecyclerView.Adapter<AllproductsAdapter.viewHolder>(){

    var quantityList = arrayListOf("1 Pcs","2 Pcs","3 Pcs","4 Pcs","5 Pcs","6 Pcs","7 Pcs","8 Pcs","9 Pcs","10 Pcs")

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

        holder.proName.text = shopProductsList.get(position).productname
        holder.proPrice.text = "Rs " + shopProductsList.get(position).productPrice.toString()
        holder.proDiscount.text = "Rs "+shopProductsList.get(position).discount
        if (shopProductsList.get(position).discount.equals(0)){
            holder?.binding?.discountTxt?.visibility = View.GONE
        }else{
            holder.discount.text = shopProductsList.get(position).discount.toString()+"%\noff"
        }
        holder.noPcs.setSelection(shopProductsList.get(position).productPcs)
        holder.binding.productImg.setImageResource(shopProductsList.get(position).productImg)

        if (shopProductsList.get(position).isAddedToCart){
            holder.removeBtn.visibility = View.VISIBLE
            holder.addBtn.visibility = View.GONE
        }else{
            holder.removeBtn.visibility = View.GONE
            holder.addBtn.visibility = View.VISIBLE
        }

        holder.addBtn.setOnClickListener {
            allProductsFrag.addtocart(shopProductsList.get(position).id)
        }

        holder.removeBtn.setOnClickListener {
            allProductsFrag.removefromcart(shopProductsList.get(position).id)
        }
    }

    override fun getItemCount(): Int {
        return shopProductsList.size
    }
}