package com.netSet.urbanstores.ui.shops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentShopsBinding

class ShopsFragment : BaseFragment() {

    var binding : FragmentShopsBinding ?=null
    var adapter : ShopListsAdapter = ShopListsAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_shops,container,false)
        rootView = binding?.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shopListAdapter()
        navigationBgVisiblity()
        setToolBar(R.mipmap.profile,"HOME",R.mipmap.bell_3x)
    }

    private fun shopListAdapter() {
        adapter = ShopListsAdapter(this)
        val manager = LinearLayoutManager(context)
        binding?.shopsRecyclerView?.setHasFixedSize(true)
        binding?.shopsRecyclerView?.layoutManager = manager
        binding?.shopsRecyclerView?.adapter = adapter
    }
}