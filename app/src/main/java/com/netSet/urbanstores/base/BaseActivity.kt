package com.netSet.urbanstores.base

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.netSet.urbanstores.R
import com.netSet.urbanstores.models.AllProductsModel
import com.netSet.urbanstores.ui.shops.ShopProducts.CartCallback
import com.netSet.urbanstores.ui.shops.ShopProducts.allProducts.AllproductsAdapter

open class BaseActivity : AppCompatActivity() {

    var currentFragment : Fragment?= null
    var fragmentTransaction : FragmentTransaction?= null
    var adapter : AllproductsAdapter?= null
    var cartCallback : CartCallback ?=null
    var productsList : ArrayList<AllProductsModel> = ArrayList()
    var cartTotalAmount : Int = 0
    var totalDiscountAmount : Int = 0

    fun replaceFragment(mFragment : Fragment, isBack : Boolean, allowAnim : Boolean){
        currentFragment = mFragment
        fragmentTransaction = supportFragmentManager.beginTransaction()

        if (isBack){ fragmentTransaction?.addToBackStack("") }

        if (allowAnim){
            fragmentTransaction?.setCustomAnimations(
                R.anim.enter,
                R.anim.exit,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )}
        fragmentTransaction?.replace(R.id.mainContainer,currentFragment!!,"")?.commitAllowingStateLoss()
    }

    //hide Keyboard on screen touch
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view: View? = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass
                .getName().startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x: Float = ev.rawX + view.getLeft() - scrcoords[0]
            val y: Float = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) (this.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager).hideSoftInputFromWindow(
                this.window.decorView.applicationWindowToken, 0
            )
        }
        return super.dispatchTouchEvent(ev)
    }
    fun addProducts() {
        productsList.add(AllProductsModel(R.mipmap.img_3,"Potato",90,"50",false,"Vegetables",0))
        productsList.add(AllProductsModel(R.mipmap.img_4,"Apple",80,"10",false,"Fruits",0))
        productsList.add(AllProductsModel(R.mipmap.img_3,"Tomato",40,"40",false,"Vegetables",0))
        productsList.add(AllProductsModel(R.mipmap.img_4,"Mango",80,"50",false,"Fruits",0))
        productsList.add(AllProductsModel(R.mipmap.img_4,"Grapes",70,"70",false,"Fruits",0))
    }


}