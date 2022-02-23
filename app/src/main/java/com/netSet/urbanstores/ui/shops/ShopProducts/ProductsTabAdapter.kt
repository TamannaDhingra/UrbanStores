package com.netSet.urbanstores.ui.shops.ShopProducts

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.netSet.urbanstores.models.AllProductsModel
import com.netSet.urbanstores.ui.shops.ShopProducts.allProducts.AllProductsFrag
import com.netSet.urbanstores.ui.shops.ShopProducts.fruits.FruitsFrag
import com.netSet.urbanstores.ui.shops.ShopProducts.packages.PackagesFrag
import com.netSet.urbanstores.ui.shops.ShopProducts.vegitables.VegitablesFrag

class ProductsTabAdapter(
    shopProductsFrag: ShopProductsFrag,
    framenetManager: FragmentManager,
    val tabCount: Int,
    productsTabLayout: TabLayout
)
    : FragmentPagerAdapter(framenetManager) {

    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0->{
                AllProductsFrag()
            }
            1->{
                FruitsFrag()
            }
            2->{
                VegitablesFrag()
            }
            3->{
                PackagesFrag()
            }
            else -> {
                getItem(position)
            }
        }
    }
}