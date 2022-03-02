package com.netSet.urbanstores.ui.shops.ShopProducts.allProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentAllProductsBinding

class AllProductsFrag() : BaseFragment() {

    var binding : FragmentAllProductsBinding ?=null
    var adapter : AllproductsAdapter ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null){
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_all_products,container,false)
            rootView = binding?.root
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        allProductsAdapterCall()
    }

    private fun allProductsAdapterCall() {

        if (getBaseActivity().tabPosition == 0){
            adapter = AllproductsAdapter(this,getBaseActivity().shopProductsList,getBaseActivity())

        }else if (getBaseActivity().tabPosition == 1){
            adapter = AllproductsAdapter(this,getBaseActivity().getFilterFruits(),getBaseActivity())

        }else if (getBaseActivity().tabPosition == 2){
            adapter = AllproductsAdapter(this,getBaseActivity().getFilterVegetable(),getBaseActivity())

        }else if (getBaseActivity().tabPosition == 3){
            adapter = AllproductsAdapter(this,getBaseActivity().getFilterPackages(),getBaseActivity())
        }

        val manager = LinearLayoutManager(context)
        binding?.allProductsRecycler?.setHasFixedSize(true)
        binding?.allProductsRecycler?.layoutManager = manager
        binding?.allProductsRecycler?.adapter = adapter

        adapter?.notifyDataSetChanged()
    }

    fun addtocart(productId: Int) {

        for(i in 0 until getBaseActivity().shopProductsList.size) {
            if (productId == getBaseActivity().shopProductsList[i].id){
                getBaseActivity().shopProductsList.get(i).isAddedToCart = true
                adapter?.notifyDataSetChanged()
            }
        }
    }

    fun removefromcart(productId: Int) {
        for(i in 0 until getBaseActivity().shopProductsList.size) {
            if (productId == getBaseActivity().shopProductsList[i].id){
                getBaseActivity().shopProductsList.get(i).isAddedToCart = false
                adapter?.notifyDataSetChanged()
            }
        }
    }
}