package com.netSet.urbanstores.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentSettingBinding
import com.netSet.urbanstores.sharePreference.AppPref
import com.netSet.urbanstores.ui.phoneVerify.PhoneVerifyFragment


class SettingFrag : BaseFragment() {

    lateinit var settingBinding: FragmentSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_setting, container, false)
        return settingBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar(0,"SETTINGS",0)
        showBottomNavigation()
        navigationBgVisiblity()

        onClick()
        sharePreferenceStoreNumber()
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
    private fun sharePreferenceStoreNumber() {
        val appPrefs = AppPref(requireActivity())
        val dataPhone=appPrefs.getValue("phone").toString()
        val dataCountry= appPrefs.getValue("country").toString()
        settingBinding.tvPhoneNumberSetting.text=dataPhone
      //  settingBinding.tvCountryCode.text= dataCountry
    }
}