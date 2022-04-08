package com.netSet.urbanstores.ui.orders.myOrders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentMyOrdersBinding
import com.netSet.urbanstores.models.ModelClssMyOrders
import com.netSet.urbanstores.sharePreference.AppPref


class FragmentMyOrders : BaseFragment() {

    lateinit var binding: FragmentMyOrdersBinding
    val arrayList = ArrayList<ModelClssMyOrders>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        arrayList.add(ModelClssMyOrders("1 X Banana-Yekali(12pcs),1X-Onion","2 june 2020 at 7:54 PM","Rs 130"))
        arrayList.add(ModelClssMyOrders("1 X Banana-Yekali(12pcs),1X-Onion","3 june 2021 at 7:54 PM","Rs 150"))
        arrayList.add(ModelClssMyOrders("1 X Banana-Yekali(12pcs),1X-Onion","4 june 2020 at 7:54 PM","Rs 170"))
        arrayList.add(ModelClssMyOrders("1 X Banana-Yekali(12pcs),1X-Onion","5 june 2021 at 7:54 PM","Rs 190"))

//        navigationBgVisiblity()
        setToolBar(AppPref(requireContext()).getUserImage(),"MY ORDERS",R.mipmap.cart_3x)
        showBottomNavigation()
        counterVisible()
     /*   (activity as MainActivity).activityMainBinding.profileImg.setOnClickListener {
            (activity as MainActivity).replaceFragment(SettingFrag(),true,false)
        }*/
        val recyclerAdapter = AdapterMyOrders(arrayList, getBaseActivity())
        binding.rvOrderInProgress.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrderInProgress.adapter = recyclerAdapter
        return binding.root


        /** for apply animations*/
        // val animation=  AnimationUtils.loadAnimation(activity,R.anim.enter_from_left)
    }


    }
