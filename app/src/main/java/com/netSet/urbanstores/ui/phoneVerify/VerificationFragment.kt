package com.netSet.urbanstores.ui.phoneVerify

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentVerificationBinding
import com.netSet.urbanstores.sharePreference.AppPref
import com.netSet.urbanstores.ui.shops.ShopsFragment
import java.util.concurrent.TimeUnit


class VerificationFragment : BaseFragment() {
    var isAppRunning=false
    var phone:String? = null
    var countryCode:String? = null
    private  var data: String ? =null
    private var storedVerificationId: String? =null
    private lateinit var auth: FirebaseAuth
    private  var resendToken: PhoneAuthProvider.ForceResendingToken?=null
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var verificationBinding: FragmentVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        verificationBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_verification, container, false)
        return verificationBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar("back","VERIFICATION CODE",0)
        hideBottomNavigation()
        isAppRunning=true
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                val code= credential.smsCode

                if (code!=null) {
                    verificationBinding.etOTP.setText(code)
                    verifyPhoneNumberWithCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                storedVerificationId = "NA"

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }
                // Show a message and update the UI
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                storedVerificationId = verificationId
                resendToken = token
            }
        }
        setInitialData()
        counterGone()
        setBackGroundRecursive()
    }

    private fun verifyPhoneNumberWithCode( code: String) {
       /* if (storedVerificationId == "NA"){
            Toast.makeText(requireActivity(), "Incorrect Otp.",Toast.LENGTH_SHORT).show()
            return
        }*/

        val credential = PhoneAuthProvider.getCredential(storedVerificationId!!,code)
            signInWithPhoneAuthCredential(credential)
            val appPrefs = AppPref(requireActivity())
            data= appPrefs.setValue("phone", phone!!).toString()

            if (data!=null||data!="") {
                verificationBinding.tvShowNumber.text=data.toString()
            }


        }



    private fun resendVerificationCode(phoneNumber: String, token: PhoneAuthProvider.ForceResendingToken?) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireActivity(),  "Login Successfully", Toast.LENGTH_SHORT).show()
                    (context as MainActivity).replaceFragment(ShopsFragment(), true, false)
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(requireActivity(),  "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show()
                    }

                }
            }
    }
    override fun onDestroy() {
        super.onDestroy()
        isAppRunning=false
    }

    private fun setInitialData() {

        if (arguments!=null){
            phone=requireArguments().getString("number")
            countryCode=requireArguments().getString("country")
            verificationBinding.tvShowNumber.text=" "+phone
//            verificationBinding.tvCountryCode.text=countryCode
            startPhoneNumberVerification(phone!!)
        }
        verificationBinding.tvResendOtp.setOnClickListener {
            resendVerificationCode(phone!!,resendToken)
        }
        verificationBinding.btnDone.setOnClickListener {
            val storePin = verificationBinding.etOTP.text
            if (storePin!!.isNotEmpty()){
                verifyPhoneNumberWithCode(storePin.toString())
            }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
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



/*
class VerificationFragment : BaseFragment() {
    var isAppRunning=false
    var phone:String? = null
    var countryCode:String? = null
    var receiveOtp:String? = null

    private  var data: String ? =null


    lateinit var retro: ApiService
    lateinit var repo: Repositories
    lateinit var viewmodel: MainViewModel


    var sendOtp : String =""
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
        counterGone()
        setBackGroundRecursive()
       */
/* (activity as MainActivity).activityMainBinding.profileImg.setOnClickListener {
            mainActivity?.onBackPressed()
        }*//*


        retro = RetroBuilder.service
        repo = Repositories(retro, activity)
        viewmodel = ViewModelProvider(this, MainViewModelFactory(activity as MainActivity))
            .get(MainViewModel::class.java)


        viewmodel.registerUserModel.observe(viewLifecycleOwner, Observer {
            (context as MainActivity).replaceFragment(ShopsFragment(), true, false)
            verificationBinding.etOTP.text?.clear()
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        isAppRunning=false
    }
    private fun setInitialData() {

        if (arguments!=null){
            phone=requireArguments().getString("number")
            countryCode=requireArguments().getString("country")
            receiveOtp=requireArguments().getString("sendotp")
            //receiveOtp="123456"


            verificationBinding.tvShowNumber.text=phone
            verificationBinding.tvCountryCode.text=countryCode
        }

        verificationBinding.btnDone.setOnClickListener {
              verifyOtp()


              val appPrefs = AppPref(requireActivity())
                data= appPrefs.setValue("phone",phone?:"").toString()
                if (data!=null||data!="")
                {
                    verificationBinding.tvShowNumber.text=data.toString()
                }
              //  (context as MainActivity).replaceFragment(ShopsFragment(), true, false)

                // verificationBinding.etOTP.text?.clear()
        }
    }

    private fun verifyOtp() {
        if (verificationBinding.etOTP.text.toString().length == 6) {
            val enterOtp = verificationBinding.etOTP.text.toString()

            val credential= PhoneAuthProvider.getCredential(receiveOtp.toString(), enterOtp)
            //sendOtp is taken from the onCodeSent Method.


            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        val user = task.result?.user
                        Toast.makeText(context, "login successful", Toast.LENGTH_SHORT).show()
                     viewmodel.callRegisterUserRes(phone.toString(),countryCode.toString())
                       // (context as MainActivity).replaceFragment(ShopsFragment(), true, false)

                        //Dashboard activity is for sign in purpose.
                    } else {
                        Toast.makeText(
                            context,
                            "wrong otp",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }else {
            Toast.makeText(
                context,
                "Otp must be 6 digits",
                Toast.LENGTH_SHORT).show()
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
*/



