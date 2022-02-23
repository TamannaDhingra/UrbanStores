package com.netSet.urbanstores.ui.shops.ShopProducts.vegitables

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseActivity
import com.netSet.urbanstores.databinding.AllproductsViewBinding
import com.netSet.urbanstores.models.AllProductsModel

class VegetablesAdapter(
    val productsList: List<AllProductsModel>,
    val fragment: VegitablesFrag,
    val baseActivity: BaseActivity
) : RecyclerView.Adapter<VegetablesAdapter.ViewHolder>() {


    var quantityList = arrayListOf("1Pcs","2Pcs","3Pcs","4Pcs","5Pcs","6Pcs","7Pcs","8Pcs","9Pcs","10Pcs")

    inner class ViewHolder(val binding : AllproductsViewBinding) : RecyclerView.ViewHolder(binding.root){
        val proName = binding.productName
        val proPrice = binding.productAmount
        val proDiscount = binding.productDiscount
        val addBtn = binding.itemAddButton
        val removeBtn = binding.itemRemoveButton
        val productDiscount = binding.discountTxt
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<AllproductsViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.allproducts_view,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.proName.text = productsList.get(position).productname
        holder.proPrice.text = productsList.get(position).productPrice.toString()
        holder.proDiscount.text = productsList.get(position).discount
        holder.productDiscount.text = productsList.get(position).discount + "% \noff"

        if (productsList.get(position).isAddedToCart){
            holder.removeBtn.visibility = View.VISIBLE
            holder.addBtn.visibility = View.GONE
        }else{
            holder.removeBtn.visibility = View.GONE
            holder.addBtn.visibility = View.VISIBLE
        }

/*        holder.addBtn.setOnClickListener {
            baseActivity.cartCallback?.addtocart(position, holder.noPcs.selectedItemPosition)
        }

        holder.removeBtn.setOnClickListener {
            baseActivity.cartCallback?.removefromcart(position)
        }*/

        //Quantity Spinner Adapter
        val adapter = ArrayAdapter(fragment.requireContext(), R.layout.quantity_spinner_layout,quantityList )
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        holder.binding.quantitys.adapter = adapter
    }

    override fun getItemCount(): Int {
        return productsList.size
    }
}