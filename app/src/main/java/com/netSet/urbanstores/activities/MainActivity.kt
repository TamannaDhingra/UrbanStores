package com.netSet.urbanstores.activities

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.snackbar.Snackbar
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseActivity
import com.netSet.urbanstores.databinding.ActivityMainBinding
import com.netSet.urbanstores.sharePreference.AppPref
import com.netSet.urbanstores.ui.cart.CartFrag
import com.netSet.urbanstores.ui.notification.NotificationFrag
import com.netSet.urbanstores.ui.orders.myOrders.FragmentMyOrders
import com.netSet.urbanstores.ui.phoneVerify.PhoneVerifyFragment
import com.netSet.urbanstores.ui.settings.SettingFrag
import com.netSet.urbanstores.ui.shops.ShopProducts.ShopProductsFrag
import com.netSet.urbanstores.ui.shops.ShopsFragment


class MainActivity : BaseActivity() {

    lateinit var activityMainBinding : ActivityMainBinding
    var appPrefs = AppPref(this)
    private  var data: String ? =null
    @RequiresApi(Build.VERSION_CODES.M)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        allProductsLists()
        onClick()
        data=appPrefs.getValue("phone")

        if (data.isNullOrEmpty()){
          replaceFragment(PhoneVerifyFragment(),true,false)
        }
        else{
            replaceFragment(ShopsFragment(), true, false)
        }
        window?.statusBarColor = this.getColor(R.color.green_bg)
        bottomNavigationCode()
//        activityMainBinding.bottomGreenBg.visibility = View.GONE
        activityMainBinding.profileImg.tag ="profileImg"
    }

    private fun onClick() {
        activityMainBinding.profileImg.setOnClickListener {
            if (getVisibleFragment() is ShopsFragment||getVisibleFragment() is FragmentMyOrders){
                activityMainBinding.bottomNavigationView.selectedItemId = R.id.settingsMenu
            }else {
                onBackStackChanged()
            }
        }
        activityMainBinding.menuIcon.setOnClickListener {
            replaceFragment(CartFrag(),true,false)
        }
    }

    private fun bottomNavigationCode() {

        activityMainBinding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeMenu ->{
                    replaceFragment(ShopsFragment(), true, false)
                    true
                }
                R.id.myorderMenu ->{
                    replaceFragment(FragmentMyOrders(), true, false)
                    true
                }
                R.id.mycartMenu ->{
                    replaceFragment(NotificationFrag(), true, false)
                    true
                }
                R.id.settingsMenu ->{
                    replaceFragment(SettingFrag(), true, false)
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
      //  super.onBackPressed()
        onBackStackChanged()
    }

    private fun getVisibleFragment(): Fragment {
        return supportFragmentManager.findFragmentById(R.id.mainContainer)!!
    }

    fun onBackStackChanged() {
        val localFragmentManager = supportFragmentManager
        val i = localFragmentManager.backStackEntryCount

        if (getVisibleFragment() is ShopsFragment) {
            dialogExitApp()
            return
        }
            if (getVisibleFragment() is ShopsFragment||getVisibleFragment() is PhoneVerifyFragment ){
                finish()
        } else if (getVisibleFragment() is FragmentMyOrders|| getVisibleFragment() is NotificationFrag||getVisibleFragment() is SettingFrag){
                activityMainBinding.bottomNavigationView.selectedItemId = R.id.homeMenu

        } else {
                localFragmentManager.popBackStack()
            }
   }

    fun showSnackBar(string: String) {
        try {
            val snackBar: Snackbar = Snackbar.make(findViewById(android.R.id.content), string, Snackbar.LENGTH_SHORT)
            val snackBarView = snackBar.view
            snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.appbar_bg))
            snackBar.show()
        }
        catch (e: java.lang.Exception) {
        }
    }

    fun dialogExitApp() {
        AlertDialog.Builder(this)
            .setMessage(R.string.exit_app)
            .setPositiveButton("Yes")
            { dialogInterface, which ->
                if (getVisibleFragment() is ShopsFragment) {
                    finish()
                }
            }.setNegativeButton(
                "No"
            ) { p0, p1 -> p0?.dismiss() }.create().show()
    }
    fun myOrder(){
        activityMainBinding.bottomNavigationView.selectedItemId = R.id.myorderMenu
    }
}