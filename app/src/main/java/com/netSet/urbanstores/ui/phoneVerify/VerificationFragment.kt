package com.netSet.urbanstores.ui.phoneVerify

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentVerificationBinding
import com.netSet.urbanstores.sharePreference.AppPref
import com.netSet.urbanstores.ui.shops.ShopsFragment

class VerificationFragment : BaseFragment() {
    var isAppRunning=false
    var phone:String? = null
    var countryCode:String? = null
    private  var data: String ? =null
    lateinit var verificationBinding: FragmentVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        verificationBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_verification, container, false)
        return verificationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar(R.mipmap.back_48x48,"VERIFICATION CODE",0)
        hideBottomNavigation()
        isAppRunning=true
        setInitialData()
        setBackGroundRecursive()
       /* (activity as MainActivity).activityMainBinding.profileImg.setOnClickListener {
            mainActivity?.onBackPressed()
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        isAppRunning=false
    }
    private fun setInitialData() {

        if (arguments!=null){
            phone=requireArguments().getString("number")
            countryCode=requireArguments().getString("country")
            verificationBinding.tvShowNumber.text=phone
            verificationBinding.tvCountryCode.text=countryCode
        }

        verificationBinding.btnDone.setOnClickListener {
                val appPrefs = AppPref(requireActivity())
                data= appPrefs.setValue("phone",phone?:"").toString()
                if (data!=null||data!="")
                {
                    verificationBinding.tvShowNumber.text=data.toString()
                }
                (context as MainActivity).replaceFragment(ShopsFragment(), true, false)

                 verificationBinding.etOTP.text?.clear()
        }
    }

    private fun setBackGroundRecursive(){
        Handler(Looper.myLooper()!!).postDelayed({
            if (isAppRunning) {
                if (verificationBinding.etOTP.text.toString().length<4) {
                    verificationBinding.btnDone.isEnabled = false
                    verificationBinding.btnDone.setBackgroundColor(getResources().getColor(R.color.btn_tint))
                    verificationBinding.btnDone.setTextColor(getResources().getColor(R.color.btn_text_clr))
                }  else {
                    verificationBinding.btnDone.isEnabled = true
                    verificationBinding.btnDone.setBackgroundColor(getResources().getColor(R.color.appbar_bg))
                    verificationBinding.btnDone.setTextColor(getResources().getColor(R.color.white))
                }
                setBackGroundRecursive()
            }
        },1)
    }

}