package com.netSet.urbanstores.base

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.netSet.urbanstores.activities.MainActivity

open class BaseFragment : Fragment() {

    private var baseActivity: BaseActivity? = null
    var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.baseActivity = activity as BaseActivity
        if (activity is MainActivity) {
            mainActivity = context as MainActivity
        }
    }

    fun getBaseActivity(): BaseActivity {
        return baseActivity!!
    }

    fun backStackCode(){
        getBaseActivity().supportFragmentManager.popBackStack()
    }

    fun setImageUsingGlide(context: Fragment, url: String, imageView: ImageView){
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

    fun showApiError(msg : String){
        Toast.makeText(context, "Error is $msg", Toast.LENGTH_SHORT).show()
    }
}