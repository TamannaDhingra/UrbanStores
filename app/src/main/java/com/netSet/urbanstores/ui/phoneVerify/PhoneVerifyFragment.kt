package com.netSet.urbanstores.ui.phoneVerify

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentPhoneVerifyBinding
import com.netSet.urbanstores.network.RetroBuilder
import com.netSet.urbanstores.network.viewmodel.MainViewModel
import com.netSet.urbanstores.network.viewmodel.MainViewModelFactory
import com.netSet.urbanstores.sharePreference.AppPref

import com.netSet.urbanstores.utills.Validation

class PhoneVerifyFragment : BaseFragment() {

    var isAppRunning=false
    lateinit var phoneBinding:FragmentPhoneVerifyBinding
    var mainViewModel : MainViewModel ?=null
    lateinit var number:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        phoneBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_phone_verify, container, false)
        return phoneBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        isAppRunning=true
        setToolBar("none","PHONE NUMBER",0)
        hideBottomNavigation()
        counterGone()
        initUI()
        recursiveReturnEmail()
        viewModelInitilization()
        setObserver()
        userLoginCall()



    }

    private fun viewModelInitilization() {
        mainViewModel = ViewModelProviders.of(this,MainViewModelFactory(mainActivity!!)).get(MainViewModel::class.java)
    }

    private fun setObserver(){
        mainViewModel?.registerUserModel?.observe(viewLifecycleOwner, Observer {
            val img=it.body.imageUrl
            AppPref(requireContext()).setUserImage("image", img as String)
            AppPref(requireContext()).setToken(it.body.token.toString())

            val bundle = Bundle()
            val fragmentNew = VerificationFragment()
            bundle.putString("number",number)
            bundle.putString("country", phoneBinding.countryCCP.selectedCountryCodeWithPlus.toString())
            fragmentNew.arguments = bundle
            getBaseActivity().replaceFragment(fragmentNew, true, false)

        })
    }


    private fun userLoginCall() {
        phoneBinding.btnNext.setOnClickListener {

             number = phoneBinding.etPhoneNumber.text.toString()

            mainViewModel?.callRegisterUserRes("+91",number)
           /* val jsonObject = JSONObject()
            jsonObject.put("phone_code","+91")
            jsonObject.put("phone_number",phoneBinding.etPhoneNumber.text.toString())
            val requestBody  = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
           */

            val countryCode = phoneBinding.countryCCP.selectedCountryCodeWithPlus.toString()




            val toast = Toast.makeText(requireContext().applicationContext, "OTP successfully sent to registered number.", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 180)
            toast.show()

        }
    }

    private fun initUI() {
        phoneBinding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().length == 15){ Validation.hideKeyboardFormUser(requireActivity()) }
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
                    phoneBinding.btnNext.setBackgroundColor(resources.getColor(R.color.btn_tint))
                    phoneBinding.btnNext.setTextColor(resources.getColor(R.color.btn_text_clr))
                } else {
                    phoneBinding.btnNext.isEnabled=true
                    phoneBinding.btnNext.setBackgroundColor(resources.getColor(R.color.appbar_bg))
                    phoneBinding.btnNext.setTextColor(resources.getColor(R.color.white))
                }
                recursiveReturnEmail()
            }
        },1)
    }
}








/*
class PhoneVerifyFragment : BaseFragment() {

    var isAppRunning=false
    lateinit var phoneBinding:FragmentPhoneVerifyBinding

   // var mainViewModel : MainViewModel ?=null
    var sendOtp : String =""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        phoneBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_phone_verify, container, false)
        return phoneBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isAppRunning=true
        setToolBar(0,"PHONE NUMBER",0)
        hideBottomNavigation()
        counterGone()
        initUI()
        recursiveReturnEmail()
     //   viewModelInitilization()
        userLoginCall()
    }

  */
/*  private fun viewModelInitilization() {
        mainViewModel = ViewModelProviders.of(this,ViewModelFactory(mainActivity!!)).get(MainViewModel::class.java)
    }
*//*


    private fun userLoginCall() {
        phoneBinding.btnNext.setOnClickListener {

            val number = "+91" + phoneBinding.etPhoneNumber.text.toString()
            val countryCode = phoneBinding.countryCCP.selectedCountryCodeWithPlus.toString()

            */
/*       mainViewModel?.getUserLogin(number)
                   mainViewModel?.userLoginMutableObj?.observe(viewLifecycleOwner){
                       loginModel = it
                   }*//*


            if (phoneBinding.etPhoneNumber.text.toString().length == 10) {

                FirebaseAuth.getInstance().addAuthStateListener {
                    val options = PhoneAuthOptions.newBuilder(it)// it-> firebase auth token.
                        .setPhoneNumber("+91" + phoneBinding.etPhoneNumber.text.toString())
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity as MainActivity)// Activity (for callback binding)
                        .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            override fun onVerificationCompleted(p0: PhoneAuthCredential){
                                Toast.makeText(context, "completed", Toast.LENGTH_SHORT).show()
                            }

                            override fun onVerificationFailed(p0: FirebaseException){
                                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                            }
                            override fun onCodeSent(otp: String, token: PhoneAuthProvider.ForceResendingToken) {
                                sendOtp = otp // i store this in one global variable
                                Toast.makeText(context, "otp send successfully", Toast.LENGTH_SHORT).show()

                                val bundle= Bundle()
                                val verificationFragment=VerificationFragment()

                                bundle.putString("number",number)
                                bundle.putString("country", countryCode)
                                bundle.putString("sendotp",sendOtp)
                                verificationFragment.arguments=bundle
                                getBaseActivity().replaceFragment(verificationFragment, true, false)

                                Log.d("TAG", "onCodeSent: In Phone Verify fragment"  +sendOtp)
                            }
                        }).build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                }

            } else {
                Toast.makeText(context, "Mobile number must be 10 Digit", Toast.LENGTH_SHORT).show()
            }

            val toast = Toast.makeText(requireContext().applicationContext, "OTP successfully sent to registered number.", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 180)
            toast.show()
        }
    }

    private fun initUI() {
        phoneBinding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().length == 15){ Validation.hideKeyboardFormUser(requireActivity()) }
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
}*/
