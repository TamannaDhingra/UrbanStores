package com.netSet.urbanstores.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentCartBinding
import com.netSet.urbanstores.ui.cart.ConfirmPickup.OrderConfirmFrag

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

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fruitsAdapter()
        vegitablesAdapter()
        navigationBgVisiblity()
        checkoutBtnListner()
        setToolBar(R.mipmap.profile,"MY CART",R.mipmap.bell_3x)
        (activity as MainActivity).activityMainBinding?.bottomGreenBg?.visibility = View.VISIBLE
        Log.e("myData",getBaseActivity().cartTotalAmount.toString())
    }

    private fun checkoutBtnListner() {
        binding?.checkoutBtn?.setOnClickListener { getBaseActivity().replaceFragment(OrderConfirmFrag(),true,false) }
    }

    private fun vegitablesAdapter() {

        val vegeList = getBaseActivity().productsList.filter {
            it.productCategory.equals("Vegetables")&&
                    it.isAddedToCart.equals(true)
        }

        vegiAdapter = VegitablesCartAdapter(this,vegeList)
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

        fruitsAdapter = FruitsCartAdapter(this,fruitsList)
        val manager = LinearLayoutManager(context)
        binding?.fruitsRecyclerview?.setHasFixedSize(true)
        binding?.fruitsRecyclerview?.layoutManager = manager
        binding?.fruitsRecyclerview?.adapter = fruitsAdapter

        binding?.fruitsCount?.text = fruitsList.size.toString() +" items"
    }

    override fun onStop() {
        super.onStop()
        getBaseActivity().cartTotalAmount = 0
        getBaseActivity().totalDiscountAmount = 0
    }

    fun setTotal() {
        binding?.totalRupee?.text = "Rs. "+getBaseActivity().cartTotalAmount.toString()
        binding?.savedRupee?.text = "Saved Rs. "+getBaseActivity().totalDiscountAmount.toString()
    }
}