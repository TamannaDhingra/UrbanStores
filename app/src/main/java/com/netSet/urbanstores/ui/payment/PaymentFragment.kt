package com.netSet.urbanstores.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentPaymentBinding


class PaymentFragment : BaseFragment() {
lateinit var paymentBinding:FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        paymentBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_payment, container, false)
        return paymentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolBar("back","MAKE PAYMENT",0)
        onClick()
        getTotalAmntUsingBundle()
        grandTotalAmnt()
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

    private fun getTotalAmntUsingBundle() {
       var ttlAmnt = arguments?.getInt("totalAmount")!!
        paymentBinding.tvRupees?.text = ttlAmnt.toString()
    }

    private fun grandTotalAmnt() {
        if (paymentBinding?.tvRupees?.text.toString().toInt()>0){
            paymentBinding?.tvDeliveryCharge?.text = 20.toString()
        }

        val totalAmnt = paymentBinding?.tvRupees?.text.toString().toInt()
        val deliveryAmnt = paymentBinding?.tvDeliveryCharge?.text.toString().toInt()
        paymentBinding?.tvGrandTotal?.text = (totalAmnt+deliveryAmnt).toString()
    }


}