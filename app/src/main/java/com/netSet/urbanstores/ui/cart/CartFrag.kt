package com.netSet.urbanstores.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentCartBinding
import com.netSet.urbanstores.models.ShopProductsList
import com.netSet.urbanstores.ui.cart.ConfirmPickup.OrderConfirmFrag

class CartFrag : BaseFragment() {

    var binding :FragmentCartBinding ?=null
    var fruitsAdapter : FruitsCartAdapter ?=null
    var vegiAdapter : VegitablesCartAdapter ?= null
    var packageAdapter : PackageCartAdapter ?=null

    var fruitsList : List<ShopProductsList> ? = null
    var vegeList : List<ShopProductsList> ? = null
    var packageList : List<ShopProductsList> ? = null

    val ISFRUIT = 1
    val ISVEGETABLE = 2
    val ISPACKAGE = 3

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
        packageAdapter()
        navigationBgVisiblity()
        checkoutBtnListner()
        ifListEmptyCode()
        setToolBar(R.mipmap.profile,"MY CART",R.mipmap.bell_3x)
        (activity as MainActivity).activityMainBinding.bottomGreenBg.visibility = View.VISIBLE
        Log.e("myData",getBaseActivity().cartTotalAmount.toString())
    }

    private fun ifListEmptyCode() {
        if (fruitsList?.isEmpty()!!&&vegeList?.isEmpty()!! && packageList?.isEmpty()!!){
            binding?.fruitsConstraint?.visibility = View.GONE
            binding?.vegitablesConstraint?.visibility = View.GONE
            binding?.packageConstraint?.visibility = View.GONE
            binding?.noDataFoundTxt?.visibility = View.VISIBLE
        }else if (fruitsList?.isEmpty()!!){
            binding?.fruitsConstraint?.visibility = View.GONE
        }else if (vegeList?.isEmpty()!!){
            binding?.vegitablesConstraint?.visibility = View.GONE
        }else if (packageList?.isEmpty()!!){
            binding?.packageConstraint?.visibility = View.GONE
        }
    }

    private fun checkoutBtnListner() {
        binding?.checkoutBtn?.setOnClickListener {
            val bundle = Bundle()
            val fragment = OrderConfirmFrag()
            bundle.putInt("totalAmount",getBaseActivity().cartTotalAmount)
            fragment.arguments = bundle
            getBaseActivity().replaceFragment(fragment, true, false)
        }
    }

    private fun packageAdapter(){
        packageList = getBaseActivity().shopProductsList.filter {
            it.productCategory.equals("Packages")&&
                    it.isAddedToCart.equals(true)
        }
        packageAdapter = PackageCartAdapter(this, packageList!!)
        val layoutManager = LinearLayoutManager(context)
        binding?.packageRecyclerview?.setHasFixedSize(true)
        binding?.packageRecyclerview?.layoutManager = layoutManager
        binding?.packageRecyclerview?.adapter = packageAdapter

        binding?.packageCount?.text = vegeList?.size.toString() +" item"

    }

    private fun vegitablesAdapter() {

        vegeList = getBaseActivity().shopProductsList.filter {
            it.productCategory.equals("Vegetables")&&
                    it.isAddedToCart.equals(true)
        }

        vegiAdapter = VegitablesCartAdapter(this, vegeList!!)
        val layoutManager = LinearLayoutManager(context)
        binding?.vegitablesRecyclerview?.setHasFixedSize(true)
        binding?.vegitablesRecyclerview?.layoutManager = layoutManager
        binding?.vegitablesRecyclerview?.adapter =vegiAdapter

        binding?.vegetableCount?.text = vegeList?.size.toString() +" Item"
    }

    private fun fruitsAdapter() {

        fruitsList = getBaseActivity().shopProductsList.filter {
            it.productCategory.equals("Fruits") &&
                    it.isAddedToCart.equals(true)
        }

        fruitsAdapter = FruitsCartAdapter(this,fruitsList)
        val manager = LinearLayoutManager(context)
        binding?.fruitsRecyclerview?.setHasFixedSize(true)
        binding?.fruitsRecyclerview?.layoutManager = manager
        binding?.fruitsRecyclerview?.adapter = fruitsAdapter

        binding?.fruitsCount?.text = fruitsList?.size.toString() +" Item"
    }

    override fun onStop() {
        super.onStop()
        getBaseActivity().cartTotalAmount = 0
        getBaseActivity().totalDiscountAmount = 0
    }

    fun setTotal() {
        binding?.totalRupee?.text = "Rs "+getBaseActivity().cartTotalAmount.toString()
        binding?.savedRupee?.text = "Saved Rs "+getBaseActivity().totalDiscountAmount.toString()
    }

    fun increaseQuantitiy(position: Int, type: Int) {
        getBaseActivity().cartTotalAmount = 0
        when(type){
            ISFRUIT->{
                fruitsList?.get(position)?.productPcs =fruitsList?.get(position)?.productPcs!! + 1
            }
            ISVEGETABLE->{
                vegeList?.get(position)?.productPcs =vegeList?.get(position)?.productPcs!! + 1
            }
            ISPACKAGE->{
                packageList?.get(position)?.productPcs =packageList?.get(position)?.productPcs!! + 1

            }
        }

        fruitsAdapter?.notifyDataSetChanged()
    }

    fun decreaseQuantity(position: Int, type: Int) {
        getBaseActivity().cartTotalAmount = 0
        when(type){
            ISFRUIT->{
                fruitsList?.get(position)?.productPcs =fruitsList?.get(position)?.productPcs!! - 1
            }
            ISVEGETABLE->{
                vegeList?.get(position)?.productPcs =vegeList?.get(position)?.productPcs!! - 1
            }
        }

        fruitsAdapter?.notifyDataSetChanged()
    }

}

