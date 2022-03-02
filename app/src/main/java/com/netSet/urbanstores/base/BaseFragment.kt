package com.netSet.urbanstores.base

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.models.AllProductsModel

open class BaseFragment : Fragment() {

    private var baseActivity: BaseActivity? = null
    var mainActivity: MainActivity? = null
    var rootView : View?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.baseActivity = context as BaseActivity
        if (activity is MainActivity) {
            mainActivity = context as MainActivity
        }
    }

    fun getBaseActivity(): BaseActivity {
        return baseActivity!!
    }

    fun setToolBar(profileIcon : Int,title: String, menuIcon : Int){
        (activity as MainActivity).activityMainBinding.profileImg.setImageResource(profileIcon)
        (activity as MainActivity).activityMainBinding.title.text = title
        (activity as MainActivity).activityMainBinding.menuIcon.setImageResource(menuIcon)
    }

    fun navigationBgVisiblity(){
        (activity as MainActivity).activityMainBinding?.bottomGreenBg?.visibility = View.GONE
    }

    fun hideToolBar(){
        (activity as MainActivity).activityMainBinding?.toolBar?.visibility = View.GONE
    }

    fun showTooBar(){
        (activity as MainActivity).activityMainBinding?.toolBar?.visibility = View.VISIBLE
    }

    fun hideBottomNavigation(){
        (activity as MainActivity).activityMainBinding?.bottomNavigationView?.visibility = View.GONE
    }

    fun showBottomNavigation(){
        (activity as MainActivity).activityMainBinding?.bottomNavigationView?.visibility = View.VISIBLE
    }

/*    fun backStackCode(){
        getBaseActivity().supportFragmentManager.popBackStack()
    }*/
    fun setImageUsingGlide(context: Fragment, url: String, imageView: ImageView){
        Glide.with(context)
            .load(url)
            .into(imageView)
    }
    fun showApiError(msg : String){
        Toast.makeText(context, "Error is $msg", Toast.LENGTH_SHORT).show()
    }


}

