package com.netSet.urbanstores.ui.shops.ShopProducts

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.netSet.urbanstores.models.AllProductsModel
import com.netSet.urbanstores.ui.shops.ShopProducts.allProducts.AllProductsFrag

class ProductsTabAdapter(fragmentManager: ShopProductsFrag)
    : FragmentStateAdapter(fragmentManager) {

    private val myFragments: ArrayList<Fragment> = ArrayList()

    fun addFragment(fragment: Fragment) {
        myFragments.add(fragment)
    }

    override fun getItemCount(): Int {
        return myFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return myFragments[position]
    }
}