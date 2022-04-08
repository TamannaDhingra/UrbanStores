package com.netSet.urbanstores.ui.cart.ConfirmPickup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentOrderConfirmBinding
import com.netSet.urbanstores.ui.payment.PaymentFragment
import com.netSet.urbanstores.ui.shops.LocationBottomSheet

class OrderConfirmFrag : BaseFragment() {

    var binding : FragmentOrderConfirmBinding ?=null
    var adapter : PopularItemsAdapter ?= null
    var ttlAmnt:Int=0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView==null){
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_order_confirm,container,false)
            rootView = binding?.root
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterCall()
//        navigationBgVisiblity()
        hideBottomNavigation()
        getTotalAmntUsingBundle()
       grandTotalAmnt()
        initUI()
        setToolBar("back","CONFIRM",0)
    }


    private fun initUI() {
        binding?.tvChangeOrderAddress?.setOnClickListener {
            val locationBottomSheet= LocationBottomSheet()
            locationBottomSheet.show(requireActivity().supportFragmentManager,"Location bottom Sheet")
        }
        binding?.confirmOrderBtn?.setOnClickListener {
            val bundle = Bundle()
            val fragment = PaymentFragment()
            bundle.putInt("totalAmount", ttlAmnt)
            fragment.arguments = bundle
            getBaseActivity().replaceFragment(fragment, true, false)
        }
    }

    private fun grandTotalAmnt() {
        if (binding?.itemtotalAmnt?.text.toString().toInt()>0){
            binding?.deliveryChargeAmnt?.text = 20.toString()
        }

        val totalAmnt = binding?.itemtotalAmnt?.text.toString().toInt()
        val deliveryAmnt = binding?.deliveryChargeAmnt?.text.toString().toInt()
        binding?.grandTotalAmnt?.text = (totalAmnt+deliveryAmnt).toString()
    }

    private fun getTotalAmntUsingBundle() {
         ttlAmnt = arguments?.getInt("totalAmount")!!
        binding?.itemtotalAmnt?.text = ttlAmnt.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideBottomNavigation()
    }

    private fun adapterCall() {
        adapter = PopularItemsAdapter(this)
        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding?.popularItemsRecycler?.setHasFixedSize(true)
        binding?.popularItemsRecycler?.layoutManager = manager
        binding?.popularItemsRecycler?.adapter = adapter
    }
}