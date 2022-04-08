package com.netSet.urbanstores.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentSettingBinding
import com.netSet.urbanstores.models.GetUserProfileDetails
import com.netSet.urbanstores.network.viewmodel.MainViewModel
import com.netSet.urbanstores.network.viewmodel.MainViewModelFactory
import com.netSet.urbanstores.sharePreference.AppPref
import com.netSet.urbanstores.ui.phoneVerify.PhoneVerifyFragment
import kotlinx.android.synthetic.main.shopproducts_view.view.*


class SettingFrag : BaseFragment() {

    lateinit var settingBinding: FragmentSettingBinding
    lateinit var viewmodel: MainViewModel
    lateinit var dataPhone:String
    lateinit var img:String
    var userProfileDetails : GetUserProfileDetails ?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_setting, container, false)
        return settingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar(AppPref(requireContext()).getUserImage(),"SETTINGS",0)
        showBottomNavigation()
//        navigationBgVisiblity()

        onClick()
        counterGone()
        sharePreferenceStoreNumber()
        initiateViewModel()
        setObserver()
        viewmodel.callUserProfileDetailsRes(dataPhone)
    }

    private fun onClick() {

        settingBinding.editIconProfile.setOnClickListener {
            (activity as MainActivity).replaceFragment(
                UpdateProfileFragment(),
                true,
                false,

            )
        }
        settingBinding.btnLogout.setOnClickListener {
            val appPrefs = AppPref(requireActivity())

            AlertDialog.Builder(requireActivity())
                .setMessage(R.string.select_logout)
                .setPositiveButton("Yes")
                { dialogInterface, which ->
                    appPrefs.delete()
                    (activity as MainActivity).replaceFragment(
                        PhoneVerifyFragment(),
                        false,
                        false,

                    )
                }.setNegativeButton("No"
                ) { p0, p1 -> p0?.dismiss() }.create().show()

        }
    }

private fun setObserver(){
    viewmodel.UserProfileDetails.observe(viewLifecycleOwner, Observer {
         settingBinding.setData = it
        img=it.body?.imageUrl.toString()
        it.body?.imageUrl?.let { it1 ->
            setImageUsingGlide(this,
                it1,settingBinding.imageProfileSetting)
        }
    })

}


    private fun initiateViewModel() {
        viewmodel = ViewModelProvider(this, MainViewModelFactory(activity as MainActivity))
            .get(MainViewModel::class.java)

    }

    private fun sharePreferenceStoreNumber() {
        val appPrefs = AppPref(requireActivity())
         dataPhone=appPrefs.getValue("phone").toString()
        val dataCountry= appPrefs.getValue("country").toString()
        settingBinding.tvPhoneNumberSetting.text=dataPhone
      //  settingBinding.tvCountryCode.text= dataCountry
    }
}