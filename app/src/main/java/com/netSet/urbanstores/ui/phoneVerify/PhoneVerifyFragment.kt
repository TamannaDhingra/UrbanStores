package com.netSet.urbanstores.ui.phoneVerify

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentPhoneVerifyBinding
import com.netSet.urbanstores.utills.Validation


class PhoneVerifyFragment : BaseFragment() {
var isAppRunning=false
    private  var data: String ? =null
lateinit var phoneBinding:FragmentPhoneVerifyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        phoneBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_phone_verify, container, false)
        return phoneBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar(0,"PHONE NUMBER",0)
        hideBottomNavigation()
        isAppRunning=true

        initUI()
        recursiveReturnEmail()
        onClick()

    }

    private fun onClick() {
        phoneBinding.btnNext.setOnClickListener {
            val number=phoneBinding.etPhoneNumber.text.toString()
            val countryCode=phoneBinding.countryCCP.selectedCountryCodeWithPlus.toString()
            val bundle= Bundle()
            val verificationFragment=VerificationFragment()
            bundle.putString("number",number)
            bundle.putString("country", countryCode)
            verificationFragment.arguments=bundle
            (context as MainActivity).replaceFragment(verificationFragment, true, false)
            Toast.makeText(requireActivity(),"OTP Successfully Send to Register Number",Toast.LENGTH_SHORT).show()
            phoneBinding.etPhoneNumber.text?.clear()
        }
    }

    private fun initUI() {
        phoneBinding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().length == 15){
                    Validation.hideKeyboardFormUser(requireActivity())
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        isAppRunning=false
    }
    private fun recursiveReturnEmail(){
        Handler(Looper.myLooper()!!).postDelayed({
            if (isAppRunning) {
                if (phoneBinding.etPhoneNumber.text.toString().length < 8) {
                    phoneBinding.btnNext.isEnabled = false
                    phoneBinding.btnNext.setBackgroundColor(getResources().getColor(R.color.btn_tint))
                    phoneBinding.btnNext.setTextColor(getResources().getColor(R.color.btn_text_clr))
                } else {
                    phoneBinding.btnNext.isEnabled=true
                    phoneBinding.btnNext.setBackgroundColor(getResources().getColor(R.color.appbar_bg))
                    phoneBinding.btnNext.setTextColor(getResources().getColor(R.color.white))
                }

                recursiveReturnEmail()
            }
        },1)
    }

/*    private fun validation(): Boolean {
            if (phoneBinding.etPhoneNumber.text.toString().length < 8){
             phoneBinding.etPhoneNumber.requestFocus()
            phoneBinding.etPhoneNumber.error = "Please Enter mini-8 digit"
                return false
        }
        return true
    }*/

}