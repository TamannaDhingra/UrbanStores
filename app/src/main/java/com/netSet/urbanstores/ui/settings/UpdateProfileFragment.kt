package com.netSet.urbanstores.ui.settings

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.github.dhaval2404.imagepicker.ImagePicker
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentUpdateProfileBinding
import com.netSet.urbanstores.sharePreference.AppPref
import com.netSet.urbanstores.utills.Validation
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

class UpdateProfileFragment : BaseFragment() {
    private var isAppRunning = false
    var imageBitmap: Bitmap? = null
    var imgPaths: File? = null
    lateinit var updateProfileBinding: FragmentUpdateProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        updateProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_update_profile, container, false)
        return updateProfileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar(R.mipmap.back_48x48,"EDIT PROFILE",0)
        isAppRunning = true
        onClick()
        initUi()
        hideBottomNavigation()
        sharePreferenceStoreNumber()
    }

    private fun sharePreferenceStoreNumber() {
        val appPrefs = AppPref(requireActivity())
        val data=appPrefs.getValue("phone").toString()
        updateProfileBinding.tvPhoneNumber.text=data
    }

    private fun onClick() {

      //  setBackGroundRecursive()
//        (activity as MainActivity).activityMainBinding.profileImg.setOnClickListener {
//            requireActivity().onBackPressed()
//        }
        updateProfileBinding.updateProfilePic.setOnClickListener {
            runtimePermission()
        }
        updateProfileBinding.btnUpdate.setOnClickListener {
      /*      if (imageBitmap == null) {
                (activity as MainActivity).showSnackBar("Please Select the Image")
                return@setOnClickListener
            }*/
            if (validation()) {
                (activity as MainActivity).replaceFragment(SettingFrag(), false, false,)
            }
        }
    }

    private fun initUi() {
        updateProfileBinding.etNameUser.filters = Validation.getEmojiFilter("'\"\\//\\", requireActivity())
        updateProfileBinding.etEmail.filters = Validation.getEmojiFilter("'\"\\//\\", requireActivity())
        updateProfileBinding.etAddress.filters = Validation.getEmojiFilter("'\"\\//\\", requireActivity())

        updateProfileBinding.etNameUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val str: String = p0.toString()
                if (str.length > 0 && str.contains(" ")) {
                    //(activity as MainActivity).showSnackBar("Space are not allowed")
                    updateProfileBinding.etNameUser.setText("")
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        updateProfileBinding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val str: String = p0.toString()
                if (str.length > 0 && str.contains(" ")) {
                  //  (activity as MainActivity).showSnackBar("Space are not allowed")
                    updateProfileBinding.etEmail.setText("")
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        updateProfileBinding.etAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val str: String = p0.toString()
                if (str.length > 0 && str.contains(" ")) {
                  //  (activity as MainActivity).showSnackBar("Space are not allowed")
                    updateProfileBinding.etAddress.setText("")
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        isAppRunning = false
    }

    private fun validation(): Boolean {

        if (updateProfileBinding.etNameUser.text.toString().trim().isNullOrEmpty()) {
            updateProfileBinding.etNameUser.requestFocus()
           updateProfileBinding.etNameUser.error="Name Can't Empty"
            return false
        }

        if (TextUtils.isEmpty(updateProfileBinding.etEmail.text.toString()) || !Validation.isValidEmail(
                updateProfileBinding.etEmail.text.toString())) {
            updateProfileBinding.etEmail.requestFocus()
            updateProfileBinding.etEmail.error="Enter correct email address"
            return false
        }
        if (updateProfileBinding.etAddress.text.toString().trim().isNullOrEmpty()) {
            updateProfileBinding.etAddress.requestFocus()
            updateProfileBinding.etEmail.error="Address Can't empty"
            return false
        }
        return true
    }


/*    private fun setBackGroundRecursive() {
        Handler(Looper.myLooper()!!).postDelayed({
            if (isAppRunning) {
                if (updateProfileBinding.etNameUser.text.toString().isNullOrEmpty() ||  updateProfileBinding.etEmail.text.toString()
                        .isNullOrEmpty() || updateProfileBinding.etAddress.text.toString().isNullOrEmpty()
                ) {
                    updateProfileBinding.btnUpdate.setBackgroundColor(resources.getColor(R.color.btn_tint))
                    updateProfileBinding.btnUpdate.setTextColor(resources.getColor(R.color.btn_text_clr))
                } else {
                    updateProfileBinding.btnUpdate.setBackgroundColor(resources.getColor(R.color.appbar_bg))
                    updateProfileBinding.btnUpdate.setTextColor(resources.getColor(R.color.white))
                }
                setBackGroundRecursive()
            }
        }, 1)
    }*/

    //image picker
    private fun imagePicker() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    //image picker activity result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri = data?.data!!
            imageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
            //convert bitmap to file
            convertBitmapToFile()
            updateProfileBinding.updateProfilePic.setImageURI(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireActivity(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun convertBitmapToFile() {
        val file = File(requireActivity().cacheDir, getFileName())
        file.createNewFile()
        val bos = ByteArrayOutputStream()
        imageBitmap?.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
        val bitmapdata = bos.toByteArray()
        val fos = FileOutputStream(file)
        fos.write(bitmapdata)
        imgPaths = file
        fos.flush()
        fos.close()
    }

    private fun getFileName(): String {
        return "${Random().nextInt(5)}testingfile_12.png"
    }

    private fun runtimePermission() {
        Dexter.withActivity(requireActivity())
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        imagePicker()
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    permissionToken: PermissionToken?
                ) {
                    permissionToken?.continuePermissionRequest()
                }

            }).withErrorListener {
                showApiError("Error Occur!")
            }
            .onSameThread().check()
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton(
            "GOTO SETTINGS"
        ) { dialog, which ->
            dialog.cancel()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", requireActivity().packageName, null)
            intent.data = uri
            startActivityForResult(intent, 101)
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }

}