package com.netSet.urbanstores.ui.orders.OrderDetailsInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentOrderDetailsInfoBinding
import com.netSet.urbanstores.models.ModelClassItems
import com.netSet.urbanstores.sharePreference.AppPref


class FragmentOrderDetailsInfo : BaseFragment() {
    lateinit var binding: FragmentOrderDetailsInfoBinding
    val arrayList = ArrayList<ModelClassItems>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentOrderDetailsInfoBinding.inflate(inflater,container,false)

        counterGone()

        arrayList.add(ModelClassItems("1X Banana-Yekalli(12 Pcs)"))
        arrayList.add(ModelClassItems("1X Onion 1Kg"))
        arrayList.add(ModelClassItems("1X Potato 1Kg"))
        arrayList.add(ModelClassItems("1X Tomato 1Kg"))

        val recyclerAdapter = AdapterOrderDetailsInfo(arrayList, requireContext())
        binding.rvItemDetails.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItemDetails.adapter = recyclerAdapter

//        navigationBgVisiblity()
        setToolBar(AppPref(requireContext()).getUserImage(),"ORDER DETAILS",0)
        return binding.root
    }



}
