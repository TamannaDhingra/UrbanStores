package com.netSet.urbanstores.ui.shops

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.R
import com.netSet.urbanstores.databinding.ShopsViewBinding

import com.netSet.urbanstores.models.ShowStoreList
import com.netSet.urbanstores.ui.shops.ShopProducts.ShopProductsFrag

class ShopListsAdapter(
    val fragment: ShopsFragment,
    val list1: ArrayList<ShowStoreList.Data>
) : RecyclerView.Adapter<ShopListsAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ShopsViewBinding)
        :RecyclerView.ViewHolder(binding.root){

        fun AllProductDetail(data: ShowStoreList.Data){
            binding.storeList = data

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ShopsViewBinding>(LayoutInflater.from(parent.context), R.layout.shops_view,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.AllProductDetail(list1.get(position))

        holder.binding.openProducts.setOnClickListener {

            val shopProductsFragment=ShopProductsFrag(list1.get(position).id.toString())
          /* val  getID= list1.body?.data?.get(position)?.id
            val bundle= Bundle()
            bundle.putString("id",getID.toString())
            shopProductsFragment.arguments=bundle
         */
            fragment.fragmentManager?.beginTransaction()?.replace(R.id.mainContainer,shopProductsFragment)?.addToBackStack("")?.commit()

            Log.d("TAG", "onBindViewHolder: ShopsAdapter " + list1.get(position).toString())
        }

           // holder.AllProductDetail(list1.body?.data?.get(position))
    }

    override fun getItemCount(): Int {
        return list1.size
    }

}