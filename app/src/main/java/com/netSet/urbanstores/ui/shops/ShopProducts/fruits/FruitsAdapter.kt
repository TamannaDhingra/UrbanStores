package com.netSet.urbanstores.ui.shops.ShopProducts.fruits

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

class FruitsAdapter(
    val fruitsFrag: FruitsFrag,
    val productsList: List<AllProductsModel>,
    val baseActivity: BaseActivity
) : RecyclerView.Adapter<FruitsAdapter.ViewHolder>() {

    var quantityList = arrayListOf("1Pcs","2Pcs","3Pcs","4Pcs","5Pcs","6Pcs","7Pcs","8Pcs","9Pcs","10Pcs")

    inner class ViewHolder(val binding : AllproductsViewBinding) : RecyclerView.ViewHolder(binding.root){
        val proName = binding.productName
        val proPrice = binding.productAmount
        val proDiscount = binding.productDiscount
        val addBtn = binding.itemAddButton
        val removeBtn = binding.itemRemoveButton
        val productDiscount = binding.discountTxt
        val itemconstraint = binding.itemConstraint
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitsAdapter.ViewHolder {
        val binding = DataBindingUtil.inflate<AllproductsViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.allproducts_view,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FruitsAdapter.ViewHolder, position: Int) {

            holder.proName.text = productsList.get(position).productname
            holder.proPrice.text = productsList.get(position).productPrice.toString()
            holder.proDiscount.text = productsList.get(position).discount
            holder.productDiscount.text = productsList.get(position).discount + "% \n off"


            if (productsList.get(position).isAddedToCart) {
                holder.removeBtn.visibility = View.VISIBLE
                holder.addBtn.visibility = View.GONE
            } else {
                holder.removeBtn.visibility = View.GONE
                holder.addBtn.visibility = View.VISIBLE
            }

            //Quantity Spinner Adapter
            val adapter = ArrayAdapter(
                fruitsFrag.requireContext(),
                R.layout.quantity_spinner_layout,
                quantityList
            )
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            holder.binding.quantitys.adapter = adapter



    }

    override fun getItemCount(): Int {
        return productsList.size
    }
}