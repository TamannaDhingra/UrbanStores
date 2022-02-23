package com.netSet.urbanstores.ui.myOrders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseFragment

class MyOrdersFrag : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationBgVisiblity()
        setToolBar(R.mipmap.profile,"MY ORDERS",R.mipmap.bell_3x)
    }

}