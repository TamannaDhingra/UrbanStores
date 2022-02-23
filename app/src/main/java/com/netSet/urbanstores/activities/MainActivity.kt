package com.netSet.urbanstores.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.netSet.urbanstores.base.BaseActivity
import com.netSet.urbanstores.R
import com.netSet.urbanstores.databinding.ActivityMainBinding
import com.netSet.urbanstores.ui.cart.CartFrag
import com.netSet.urbanstores.ui.myOrders.MyOrdersFrag
import com.netSet.urbanstores.ui.notification.NotificationFrag
import com.netSet.urbanstores.ui.settings.SettingFrag
import com.netSet.urbanstores.ui.shops.ShopsFragment

class MainActivity : BaseActivity() {

    var activityMainBinding : ActivityMainBinding ?=null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.mainContainer,ShopsFragment()).commit()
        getWindow()?.setStatusBarColor(this.getColor(R.color.green_bg))

        openNotificationFrag()
        bottomNavigationcode()
        addProducts()
        activityMainBinding?.bottomGreenBg?.visibility = View.GONE
    }

    private fun bottomNavigationcode() {
        activityMainBinding?.bottomNavigationView?.setItemIconTintList(null)
        activityMainBinding?.bottomNavigationView?.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeMenu ->{
                    supportFragmentManager.beginTransaction().replace(R.id.mainContainer,ShopsFragment()).commit()
                    true
                }
                R.id.myorderMenu ->{
                    supportFragmentManager.beginTransaction().replace(R.id.mainContainer,MyOrdersFrag()).commit()
                    true
                }
                R.id.mycartMenu ->{
                    supportFragmentManager.beginTransaction().replace(R.id.mainContainer,CartFrag()).commit()
                    true
                }
                R.id.settingsMenu ->{
                    supportFragmentManager.beginTransaction().replace(R.id.mainContainer,SettingFrag()).commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun openNotificationFrag() {
        activityMainBinding?.menuIcon?.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.mainContainer,NotificationFrag()).addToBackStack(null).commit()
        }
    }
}