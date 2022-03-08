package com.netSet.urbanstores.ui.shops.ShopProducts

import ShopDiscountsAdapters
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentShopProductsBinding
import com.netSet.urbanstores.ui.shops.ShopProducts.allProducts.AllProductsFrag

class ShopProductsFrag : BaseFragment(), TabLayout.OnTabSelectedListener {

    var binding : FragmentShopProductsBinding ?=null
    var adapter : ShopDiscountsAdapters ?=null
    var productsTabAdapter : ProductsTabAdapter ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView== null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_products, container, false)
            rootView = binding?.root
            setupViewPager()
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        discountsAdapterCall()
        hideBottomNavigation()
        counterVisible()
        searchTxtWatcher()

        setToolBar(R.mipmap.back_48x48,"SHOP NAME",R.mipmap.cart_3x)
    }

    private fun searchTxtWatcher() {
            binding?.searchProducts?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    if (binding?.searchProducts?.getText().toString().startsWith(" "))
                        binding?.searchProducts?.setText("") }
            })
    }

    private fun setupViewPager() {

        productsTabAdapter = ProductsTabAdapter(this)
        productsTabAdapter?.addFragment(AllProductsFrag())
        productsTabAdapter?.addFragment(AllProductsFrag())
        productsTabAdapter?.addFragment(AllProductsFrag())
        productsTabAdapter?.addFragment(AllProductsFrag())
        binding?.productsViewPager?.adapter = productsTabAdapter

        binding?.productsViewPager?.setSaveEnabled(false)

        binding?.productsViewPager?.clipToPadding = false
        binding?.productsViewPager?.clipChildren = false
        binding?.productsViewPager?.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding?.productsViewPager?.getChildAt(0)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding?.productsTabLayout?.addOnTabSelectedListener(this)

        TabLayoutMediator(binding?.productsTabLayout!!, binding?.productsViewPager!!) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "All"
                }
                1-> {
                    tab.text = "Fruit"
                }
                2 -> {
                    tab.text = "Vegetable"
                }
                3 -> {
                    tab.text = "Packages"
                }
            }
        }.attach()
    }

    private fun discountsAdapterCall() {
        val viewPagerAdapter = ShopDiscountsAdapters(context)
        binding?.discountProducts?.adapter = viewPagerAdapter
        viewPagerAdapter.notifyDataSetChanged()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        getBaseActivity().tabPosition = tab?.position!!
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
}