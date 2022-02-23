package com.netSet.urbanstores.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentCartBinding

class CartFrag : BaseFragment() {

    var binding :FragmentCartBinding ?=null
    var fruitsAdapter : FruitsCartAdapter ?=null
    var vegiAdapter : VegitablesCartAdapter ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null){
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false)
            rootView =  binding?.root
        }
        return rootView
        binding?.totalRupee?.text =  getBaseActivity().cartTotalAmount.toString()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.totalRupee?.text =  getBaseActivity().cartTotalAmount.toString()
        getBaseActivity().cartTotalAmount = 0
        fruitsAdapter()
        vegitablesAdapter()
        navigationBgVisiblity()
        setToolBar(R.mipmap.profile,"MY CART",R.mipmap.bell_3x)
        (activity as MainActivity).activityMainBinding?.bottomGreenBg?.visibility = View.VISIBLE
    }

    private fun vegitablesAdapter() {

        val vegeList = getBaseActivity().productsList.filter {
            it.productCategory.equals("Vegetables")&&
                    it.isAddedToCart.equals(true)
        }

        vegiAdapter = VegitablesCartAdapter(this,vegeList,getBaseActivity())
        val layoutManager = LinearLayoutManager(context)
        binding?.vegitablesRecyclerview?.setHasFixedSize(true)
        binding?.vegitablesRecyclerview?.layoutManager = layoutManager
        binding?.vegitablesRecyclerview?.adapter =vegiAdapter

        binding?.vegetableCount?.text = vegeList.size.toString() +" items"
    }

    private fun fruitsAdapter() {

        val fruitsList = getBaseActivity().productsList.filter {
            it.productCategory.equals("Fruits") &&
                    it.isAddedToCart.equals(true)
        }

        fruitsAdapter = FruitsCartAdapter(this,fruitsList,getBaseActivity())
        val manager = LinearLayoutManager(context)
        binding?.fruitsRecyclerview?.setHasFixedSize(true)
        binding?.fruitsRecyclerview?.layoutManager = manager
        binding?.fruitsRecyclerview?.adapter = fruitsAdapter

        binding?.fruitsCount?.text = fruitsList.size.toString() +" items"
    }
}