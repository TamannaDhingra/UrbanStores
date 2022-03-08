package com.netSet.urbanstores.ui.payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide.init
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentPaymentBinding


class PaymentFragment : BaseFragment() {
lateinit var paymentBinding:FragmentPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        paymentBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_payment, container, false)
        return paymentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolBar(R.mipmap.back_48x48,"MAKE PAYMENT",0)
        onClick()
    }

    private val congratsDialog by lazy {
        CongratulationsDialogFragment()
    }

    private fun onClick() {

        paymentBinding.btnPaymentDone.setOnClickListener {
            if (congratsDialog.isVisible){
                return@setOnClickListener
            }
            congratsDialog.show(requireFragmentManager(),"CongratulationDialog")
        }
    }
}