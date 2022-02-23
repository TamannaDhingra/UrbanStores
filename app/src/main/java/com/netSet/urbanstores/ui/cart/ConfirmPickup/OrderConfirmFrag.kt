package com.netSet.urbanstores.ui.cart.ConfirmPickup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentOrderConfirmBinding

class OrderConfirmFrag : BaseFragment() {

    var binding : FragmentOrderConfirmBinding ?=null
    var adapter : PopularItemsAdapter ?= null

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
        navigationBgVisiblity()
        hideBottomNavigation()
        setToolBar(R.mipmap.back_48x48,"CONFIRM",0)
        (activity as MainActivity).activityMainBinding?.profileImg?.setOnClickListener {
            backStackCode()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        showBottomNavigation()
    }
    private fun adapterCall() {
        adapter = PopularItemsAdapter(this)
        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding?.popularItemsRecycler?.setHasFixedSize(true)
        binding?.popularItemsRecycler?.layoutManager = manager
        binding?.popularItemsRecycler?.adapter = adapter
    }

}