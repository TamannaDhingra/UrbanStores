package com.netSet.urbanstores.ui.payment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.load.model.Model
import com.netSet.urbanstores.R
import com.netSet.urbanstores.databinding.FragmentCongratulationsDialogBinding
import com.netSet.urbanstores.databinding.FragmentVerificationBinding
import com.netSet.urbanstores.models.BottomSheetLocationModel

class CongratulationsDialogFragment : DialogFragment() {
    lateinit var dialogBinding: FragmentCongratulationsDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.setCanceledOnTouchOutside(false)
        dialogBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_congratulations_dialog, container, false)
        return dialogBinding.root
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window?.setLayout(width, height)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
    }

    private fun onClick() {
        dialogBinding.crossDismissDialog.setOnClickListener {
            dismiss()
        }

    }

}