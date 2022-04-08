package com.netSet.urbanstores.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentNotificationBinding
import com.netSet.urbanstores.sharePreference.AppPref

class NotificationFrag : BaseFragment() {

    var binding : FragmentNotificationBinding ?=null
    var adapter : NotificationAdapter = NotificationAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView==null){
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_notification,container,false)
            rootView = binding?.root
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationAdapter()
//        navigationBgVisiblity()
        showBottomNavigation()
        counterGone()
        setToolBar("none","NOTIFICATIONS",0)
   /*     (activity as MainActivity).activityMainBinding?.profileImg?.setOnClickListener {
            backStackCode()
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        setToolBar(AppPref(requireContext()).getUserImage(),"HOME", R.mipmap.bell_3x)
    }

    private fun notificationAdapter() {
        val manager = LinearLayoutManager(context)
        binding?.notificationRecyclerview?.setHasFixedSize(true)
        binding?.notificationRecyclerview?.layoutManager = manager
        binding?.notificationRecyclerview?.adapter = adapter
    }
}