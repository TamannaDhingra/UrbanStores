package com.netSet.urbanstores.ui.shops.ShopProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentShopProductsBinding
import com.netSet.urbanstores.models.AllProductsModel

class ShopProductsFrag : BaseFragment() {

    var binding : FragmentShopProductsBinding ?=null
    var adapter : ShopDiscountsAdapters = ShopDiscountsAdapters()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView== null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_products, container, false)
            rootView = binding?.root
            viewPagerCode()
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        discountsAdapterCall()
    }

    private fun viewPagerCode() {

        binding?.productsTabLayout?.addTab(binding?.productsTabLayout?.newTab()?.setText("All")!!)
        binding?.productsTabLayout?.addTab(
            binding?.productsTabLayout?.newTab()?.setText("Fruits")!!
        )
        binding?.productsTabLayout?.addTab(
            binding?.productsTabLayout?.newTab()?.setText("Vegitables")!!
        )
        binding?.productsTabLayout?.addTab(
            binding?.productsTabLayout?.newTab()?.setText("Packages")!!
        )
        binding?.productsTabLayout?.setTabGravity(TabLayout.GRAVITY_FILL)

        val adapter = ProductsTabAdapter(
            this, childFragmentManager, binding?.productsTabLayout?.tabCount!!,
            binding?.productsTabLayout!!)

        binding?.productsViewPager?.adapter = adapter

        binding?.productsViewPager?.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding?.productsTabLayout?.getTabAt(position)?.select()
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding?.productsTabLayout?.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding?.productsViewPager?.currentItem = tab?.position!!
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun discountsAdapterCall() {
        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding?.discountProducts?.setHasFixedSize(true)
        binding?.discountProducts?.layoutManager = manager
        binding?.discountProducts?.adapter = adapter
    }

}