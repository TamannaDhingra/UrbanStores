package com.netSet.urbanstores.ui.orders.orderDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentOrderDetailsBinding
import com.netSet.urbanstores.models.ModelClssMyOrders
import com.netSet.urbanstores.models.ModelClssTrackOrder

import com.netSet.urbanstores.ui.maps.MapsFragment
import com.netSet.urbanstores.ui.orders.OrderDetailsInfo.FragmentOrderDetailsInfo
import com.netSet.urbanstores.ui.orders.myOrders.AdapterMyOrders

class FragmentOrderDetails : BaseFragment() {
    lateinit var binding: FragmentOrderDetailsBinding
    val arrayList = ArrayList<ModelClssTrackOrder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      binding= FragmentOrderDetailsBinding.inflate(inflater,container,false)

        replaceMapFragment(MapsFragment())
        navigationBgVisiblity()
        setToolBar(R.mipmap.back_48x48,"ORDER DETAILS",0)
        (activity as MainActivity).activityMainBinding?.profileImg?.setOnClickListener {
            backStackCode()
        }

        arrayList.add(ModelClssTrackOrder("Order Placed","2 june 2020 at 7:54 PM",R.mipmap.check))
        arrayList.add(ModelClssTrackOrder("Order Accepted","2 june 2020 at 7:54 PM",R.mipmap.check))
        arrayList.add(ModelClssTrackOrder("Order Packed","2 june 2020 at 7:54 PM",R.mipmap.check))
        arrayList.add(ModelClssTrackOrder("On The Way","2 june 2020 at 7:54 PM",R.mipmap.check))
        arrayList.add(ModelClssTrackOrder("Delivered","2 june 2020 at 7:54 PM",R.mipmap.check))

        val recyclerAdapter = AdapterOrderDetails(arrayList, requireContext())
        binding.rvTrackOrder.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTrackOrder.adapter = recyclerAdapter


        binding.cardView.setOnClickListener {
            getBaseActivity()?.replaceFragment(FragmentOrderDetailsInfo(),true,false)
        }

        return binding.root
    }

    fun replaceMapFragment(fragment: Fragment){
        if (fragment!=null) {
            val transcation = activity?.supportFragmentManager?.beginTransaction()
            transcation?.replace(R.id.map_container, fragment)
            transcation?.commit()


        }
    }

    }
