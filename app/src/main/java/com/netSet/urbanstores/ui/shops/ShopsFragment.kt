package com.netSet.urbanstores.ui.shops

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.devs.readmoreoption.ReadMoreOption
import com.google.android.gms.location.*
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentShopsBinding
import com.netSet.urbanstores.sharePreference.AppPref
import com.netSet.urbanstores.ui.settings.SettingFrag
import com.netSet.urbanstores.utills.Validation
import java.io.IOException
import java.util.*


class ShopsFragment : BaseFragment() {
    private var readMoreOption: ReadMoreOption? =null
    var PERMISSION_ID = 44
    var mFusedLocationClient: FusedLocationProviderClient? = null
    var binding : FragmentShopsBinding ?=null
    var adapter : ShopListsAdapter = ShopListsAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_shops,container,false)
        rootView = binding?.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appPrefs = AppPref(requireActivity())

        binding?.expendTxt?.setOnClickListener {
            if (binding?.currentLocation?.lineCount ==1){
                binding?.currentLocation?.maxLines = 3
            }else{
                binding?.currentLocation?.maxLines = 1
            }
        }

        searchTxtWatcher()

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

            readMoreOption?.addReadMoreTo(binding?.currentLocation,abc)
        }
        if (checkPermissions()) {
            if (appPrefs.getValue("location")!="") {
                binding?.currentLocation?.text=appPrefs.getValue("location")
            }else{
                getLastLocation()
            }
        }else{
            requestPermissions()
        }

        shopListAdapter()
        showBottomNavigation()
//        navigationBgVisiblity()
        counterVisible()
        setToolBar(R.mipmap.profile,"HOME",R.mipmap.cart_3x)
    }

    private fun searchTxtWatcher() {
        binding?.searchProducts?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (binding?.searchProducts?.getText().toString().startsWith(" "))
                    binding?.searchProducts?.setText("") }
        })
    }

    private fun shopListAdapter() {
        adapter = ShopListsAdapter(this)
        val manager = LinearLayoutManager(context)
        binding?.shopsRecyclerView?.setHasFixedSize(true)
        binding?.shopsRecyclerView?.layoutManager = manager
        binding?.shopsRecyclerView?.adapter = adapter

        binding?.currentLocation?.setOnClickListener {
            val locationBottomSheet=LocationBottomSheet()
            locationBottomSheet.isCancelable=true
            locationBottomSheet.show(requireActivity().supportFragmentManager,"BottomSheetLocation")
        }
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
                        binding?.currentLocation?.text=Validation.getAddress(location.latitude,location.longitude, mainActivity!!
                        )
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
            binding?.currentLocation?.text=Validation.getAddress(mLastLocation.latitude,mLastLocation.longitude,mainActivity!!)
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