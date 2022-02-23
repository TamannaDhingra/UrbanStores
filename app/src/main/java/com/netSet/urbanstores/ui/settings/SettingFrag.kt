package com.netSet.urbanstores.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseFragment

class SettingFrag : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolBar(R.mipmap.profile,"SETTINGS",R.mipmap.bell_3x)
        navigationBgVisiblity()
    }
}