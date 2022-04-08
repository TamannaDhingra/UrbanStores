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
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentUpdateProfileBinding
import com.netSet.urbanstores.network.RetroBuilder
import com.netSet.urbanstores.network.viewmodel.MainViewModel
import com.netSet.urbanstores.network.viewmodel.MainViewModelFactory
import com.netSet.urbanstores.sharePreference.AppPref
import com.netSet.urbanstores.utills.Validation
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

class UpdateProfileFragment : BaseFragment(), TextWatcher {
    private var isAppRunning = false
    var imageBitmap: Bitmap? = null
    var imgPaths: File? = null
    lateinit var viewmodel: MainViewModel
    lateinit var updateProfileBinding: FragmentUpdateProfileBinding

    lateinit var name:String
    lateinit var email:String
    lateinit var profile_img:String
    lateinit var address:String
    lateinit var phone:String

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

        setToolBar("back","EDIT PROFILE",0)
        isAppRunning = true
        onClick()
        counterGone()
//        initUi()
        textWatchersValidation()
        hideBottomNavigation()
        sharePreferenceStoreNumber()

        initiateViewModel()
        setObserver()
    }

    private fun textWatchersValidation() {
        updateProfileBinding.etNameUser.addTextChangedListener(this)
        updateProfileBinding.etEmail.addTextChangedListener(this)
        updateProfileBinding.etAddress.addTextChangedListener(this)
    }

    private fun sharePreferenceStoreNumber() {
        val appPrefs = AppPref(requireActivity())
        val data=appPrefs.getValue("phone").toString()
        updateProfileBinding.tvPhoneNumber.text=data
    }

    private fun initiateViewModel() {
        viewmodel = ViewModelProvider(this, MainViewModelFactory(activity as MainActivity))
            .get(MainViewModel::class.java)

    }
    private fun setObserver() {
        viewmodel.UpdateProfileDetails.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            storeImageSF(it.body?.imageUrl)
            (activity as MainActivity).replaceFragment(SettingFrag(), false, false)
        })
    }

    private fun storeImageSF(imageUrl: String?) {
        val appPrefs = AppPref(requireActivity())
        appPrefs.setValue("image", imageUrl.toString())
    }
        private fun onClick() {

        updateProfileBinding.updateProfilePic.setOnClickListener {
            runtimePermission()
        }
        updateProfileBinding.btnUpdate.setOnClickListener {

            name = updateProfileBinding.etNameUser.text.toString()
            email = updateProfileBinding.etEmail.text.toString()
            address = updateProfileBinding.etAddress.text.toString()
            phone = updateProfileBinding.tvPhoneNumber.text.toString()

            if (validation()) {
                Toast.makeText(context, RetroBuilder.authToken, Toast.LENGTH_LONG).show()
                viewmodel.callUpdateUserProfileDetailRes(phone,name,email,profile_img,address)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        isAppRunning = false
    }

    private fun validation(): Boolean {

        if (updateProfileBinding.etNameUser.text.toString().trim().isNullOrEmpty()) {
            updateProfileBinding.etNameUser.requestFocus()
           updateProfileBinding.etNameUser.error="Name can't be Empty."
            return false
        }

        if (TextUtils.isEmpty(updateProfileBinding.etEmail.text.toString()) || !Validation.isValidEmail(
                updateProfileBinding.etEmail.text.toString())) {
            updateProfileBinding.etEmail.requestFocus()
            updateProfileBinding.etEmail.error="Email can't be Empty."
            return false
        }


        if (updateProfileBinding.etAddress.text.toString().trim().isNullOrEmpty()) {
            updateProfileBinding.etAddress.requestFocus()
            updateProfileBinding.etAddress.error="Address can't be empty."
            return false
        }
        return true
    }




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
            profile_img = uri.toString()
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
                Manifest.permission.WRITE_EXTERNAL_STORAGE
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

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {

        updateProfileBinding.etNameUser.filters = Validation.getEmojiFilter("'\"\\//\\", requireActivity())
        updateProfileBinding.etEmail.filters = Validation.getEmojiFilter("'\"\\//\\", requireActivity())
        updateProfileBinding.etAddress.filters = Validation.getEmojiFilter("'\"\\//\\", requireActivity())

        if (updateProfileBinding.etNameUser.getText().toString().startsWith(" "))
            updateProfileBinding.etNameUser.setText("")

        val result: String = p0.toString().replace(" ".toRegex(), "")

        if (p0.toString() != result) {
            updateProfileBinding.etEmail.setText(result)
            updateProfileBinding.etEmail.setSelection(result.length)
        }
        if (updateProfileBinding.etAddress.getText().toString().startsWith(" "))
            updateProfileBinding.etAddress.setText("" )

    }

}