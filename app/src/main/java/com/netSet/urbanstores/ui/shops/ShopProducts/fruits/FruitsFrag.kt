package com.netSet.urbanstores.ui.shops.ShopProducts.fruits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentFruitsBinding

class FruitsFrag : BaseFragment() {

    var adapter : FruitsAdapter ?= null
    var binding : FragmentFruitsBinding ?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { if (rootView== null){
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_fruits,container,false)
            rootView = binding?.root
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterCall()
    }

    private fun adapterCall() {
        val fruitList = getBaseActivity().productsList.filter {
            it.productCategory.equals("Fruits")
        }
        adapter = FruitsAdapter(this,fruitList,getBaseActivity())
        var manager = LinearLayoutManager(context)
        binding?.fruitsLists?.setHasFixedSize(true)
        binding?.fruitsLists?.layoutManager = manager
        binding?.fruitsLists?.adapter =adapter
    }
}