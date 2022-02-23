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
import com.netSet.urbanstores.ui.shops.ShopProducts.CartCallback

class AllProductsFrag() : BaseFragment(),CartCallback {

    var binding : FragmentAllProductsBinding ?=null
    var adapter : AllproductsAdapter ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null){
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_all_products,container,false)
            rootView = binding?.root
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allProductsAdapterCall()
        getBaseActivity().cartCallback = this
    }

    private fun allProductsAdapterCall() {
        adapter = AllproductsAdapter(this,getBaseActivity().productsList,getBaseActivity())
        val manager = LinearLayoutManager(context)
        binding?.allProductsRecycler?.setHasFixedSize(true)
        binding?.allProductsRecycler?.layoutManager = manager
        binding?.allProductsRecycler?.adapter = adapter
    }

    override fun addtocart(position: Int, selectedItem: Int) {
        getBaseActivity().productsList.get(position).productPcs = selectedItem
        getBaseActivity().productsList.get(position).isAddedToCart = true
        adapter?.notifyDataSetChanged()
    }

    override fun removefromcart(position: Int) {
        getBaseActivity().productsList.get(position).isAddedToCart = false
        adapter?.notifyDataSetChanged()
    }
}