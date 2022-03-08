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
import com.netSet.urbanstores.models.ShopProductsList
import com.netSet.urbanstores.ui.shops.ShopProducts.CartCallback
import com.netSet.urbanstores.ui.shops.ShopProducts.allProducts.AllproductsAdapter

open class BaseActivity : AppCompatActivity() {

    var currentFragment : Fragment?= null
    var fragmentTransaction : FragmentTransaction?= null
    var adapter : AllproductsAdapter?= null
    var shopProductsList : ArrayList<ShopProductsList> = ArrayList()
    var cartTotalAmount : Int = 0
    var tabPosition : Int = 0
    var itemCounts : Int = 0
    var totalDiscountAmount : Int = 0

    fun replaceFragment(mFragment: Fragment, isBack: Boolean, allowAnim: Boolean){
        currentFragment = mFragment
        fragmentTransaction = supportFragmentManager.beginTransaction()

        if (isBack){ fragmentTransaction!!.addToBackStack(mFragment::class.java.simpleName) }

        if (allowAnim){
            fragmentTransaction?.setCustomAnimations(
                R.anim.enter,
                R.anim.exit,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )}
        fragmentTransaction?.replace(R.id.mainContainer,currentFragment!!, mFragment::class.java.simpleName)?.commit()
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

    fun allProductsLists(){
        shopProductsList.add(ShopProductsList(1,R.mipmap.img_4,"Banana - Yelakki",90,10,false,"Fruits",""))
        shopProductsList.add(ShopProductsList(2,R.mipmap.img_3,"Fresh Onion",40,10,false,"Vegetables",""))
        shopProductsList.add(ShopProductsList(3,R.mipmap.img_2,"Green Salad Package",150,0,false,"Packages",""))
        shopProductsList.add(ShopProductsList(4,R.mipmap.img_4,"Potato",90,20,false,"Vegetables",""))
    }
    fun clearBackStack(){
       val count = supportFragmentManager.backStackEntryCount
        if (count>0){
            for (i in 0..count){
                supportFragmentManager.popBackStack()
            }
        }
    }
    //filtered fruits
    fun getFilterFruits(): ArrayList<ShopProductsList> {
        val fruitList = shopProductsList.filter {
            it.productCategory.equals("Fruits")
        }
        return fruitList as ArrayList<ShopProductsList>
    }

    //filtered vegetables //second
    fun getFilterVegetable(): ArrayList<ShopProductsList> {
        var vegeList = shopProductsList.filter {
            it.productCategory.equals("Vegetables")
        }
        return vegeList as ArrayList<ShopProductsList>
    }

    //filtered packges
    fun getFilterPackages(): ArrayList<ShopProductsList> {
        var packageList = shopProductsList.filter {
            it.productCategory.equals("Packages")
        }
        return packageList as ArrayList<ShopProductsList>
    }


}