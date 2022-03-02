package com.netSet.urbanstores.ui.shops

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.devs.readmoreoption.ReadMoreOption
import com.google.android.gms.location.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.adapter.BottomLocationDataAdapter
import com.netSet.urbanstores.databinding.FragmentLocationBottomSheetBinding
import com.netSet.urbanstores.models.BottomSheetLocationModel
import com.netSet.urbanstores.sharePreference.AppPref
import com.netSet.urbanstores.ui.locationFused.CurrentLocation
import com.netSet.urbanstores.utills.Validation

class LocationBottomSheet : BottomSheetDialogFragment() {
    private var readMoreOption: ReadMoreOption? =null
    var PERMISSION_ID = 44
    var mFusedLocationClient: FusedLocationProviderClient? = null
lateinit var bottomSheetLocationBinding:FragmentLocationBottomSheetBinding
private var bottomLocationDataAdapter:BottomLocationDataAdapter? = null
    val listLocation:ArrayList<BottomSheetLocationModel> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)//set corner of bottom sheet
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomSheetLocationBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_location_bottom_sheet, container, false)
        return bottomSheetLocationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appPrefs = AppPref(requireActivity())
        bottomSheetLocationBinding.tvCurrentLocation.text=appPrefs.getValue("location")

        recyclerBottomLocation()
        listBottomLocation()
        onClick()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (appPrefs.getValue("location")!=""){
            val abc=appPrefs.getValue("location").toString()
            readMoreOption = ReadMoreOption.Builder(requireContext())
                .textLength(2,ReadMoreOption.TYPE_LINE)
                .moreLabel("")
                .lessLabel("...")
                .moreLabelColor(Color.parseColor("#002C5B"))
                .lessLabelColor(Color.parseColor("#002C5B"))
                .labelUnderLine(false)
                .expandAnimation(false).build()

            readMoreOption?.addReadMoreTo(bottomSheetLocationBinding.tvCurrentLocation,abc)
        }
        if (checkPermissions()) {
            if (appPrefs.getValue("location")!="") {
                bottomSheetLocationBinding.tvCurrentLocation.text=appPrefs.getValue("location")
            }else{
                getLastLocation()
            }
        }else{
            requestPermissions()
        }
    }

    private fun onClick() {
        bottomSheetLocationBinding.crossDialogLocation.setOnClickListener {
            dismiss()
        }
        bottomSheetLocationBinding.cons.setOnClickListener {
            dismiss()
            (activity as MainActivity).replaceFragment(CurrentLocation(), true, false)
        }
    }

    private fun listBottomLocation() {
        listLocation.clear()
        listLocation.add(BottomSheetLocationModel("Industrial area,Mohali"))
        listLocation.add(BottomSheetLocationModel("Industrial area,Mohali"))
        listLocation.add(BottomSheetLocationModel("Industrial area,Mohali"))
        listLocation.add(BottomSheetLocationModel("Industrial area,Mohali"))
    }

    private fun recyclerBottomLocation() {
       bottomLocationDataAdapter= BottomLocationDataAdapter(listLocation)
        bottomSheetLocationBinding.rvBottomLocation.adapter=bottomLocationDataAdapter
    }
    fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                mFusedLocationClient!!.lastLocation.addOnCompleteListener { task ->
                    ///   val convertBtm = drawableToBitmap(resources.getDrawable(R.drawable.resize_location_icon))
                    val location = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        bottomSheetLocationBinding?.tvCurrentLocation?.text=
                            Validation.getAddress(location.latitude,location.longitude,requireContext())
                    }
                }
            } else {
                Toast.makeText(requireActivity(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        mFusedLocationClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper()!!)
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            bottomSheetLocationBinding?.tvCurrentLocation?.text=
                Validation.getAddress(mLastLocation.latitude,mLastLocation.longitude,requireContext()!!)
        }
    }
    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    // method to request for permissions
    private fun requestPermissions() {
        requestPermissions( arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ), PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }
}