package com.netSet.urbanstores.ui.shops.ShopProducts.vegitables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentVegitablesBinding

class VegitablesFrag : BaseFragment() {

    var binding : FragmentVegitablesBinding ?=null
    var adapter : VegetablesAdapter ?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView==null){
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_vegitables,container,false)
            rootView = binding?.root
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterCall()
    }

    private fun adapterCall() {
        var vegeList = getBaseActivity().productsList?.filter {
            it.productCategory.equals("Vegetables")
        }
        adapter = VegetablesAdapter(vegeList,this,getBaseActivity())
        val manager = LinearLayoutManager(context)
        binding?.vegetablesList?.setHasFixedSize(true)
        binding?.vegetablesList?.layoutManager = manager
        binding?.vegetablesList?.adapter =adapter
    }
}