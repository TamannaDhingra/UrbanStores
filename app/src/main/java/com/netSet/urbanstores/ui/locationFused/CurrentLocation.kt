package com.netSet.urbanstores.ui.locationFused

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentCurrentLocationBinding
import com.netSet.urbanstores.databinding.PopupBinding
import com.netSet.urbanstores.sharePreference.AppPref
import com.netSet.urbanstores.ui.shops.ShopsFragment
import java.io.IOException
import java.util.*


class CurrentLocation : BaseFragment() {
    private val name: String?=null
    var isAppRunning=false
    var mFusedLocationClient: FusedLocationProviderClient? = null
    var latitudeTextView: String? = null
    var longitTextView: String?= null
    var PERMISSION_ID = 44
    var passData:String?=null
    private lateinit var mMap: GoogleMap

    lateinit var bindingMap:FragmentCurrentLocationBinding
    private val callback = OnMapReadyCallback { googleMap ->
        mMap=googleMap
        recursiveOperation()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingMap= DataBindingUtil.inflate(inflater,R.layout.fragment_current_location, container, false)
        return bindingMap.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     counterGone()
        isAppRunning=true
        onClick()
        setToolBar(R.mipmap.back_48x48,"Choose Delivery Location",0)
        hideBottomNavigation()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLastLocation()
        val appPrefs = AppPref(requireContext())
        appPrefs.getValue("location")
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapCurrent) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        bindingMap.linearCurrentLocation.setOnClickListener {
            val coordinate = LatLng(latitudeTextView!!.toDouble(), longitTextView!!.toDouble())
            val yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 5f)
            mMap.animateCamera(yourLocation)
            val cameraPosition = CameraPosition.Builder()
                .target(coordinate) // Sets the center of the map to Mountain View
                .zoom(17f) // Sets the zoom
                .bearing(90f) // Sets the orientation of the camera to east
                .tilt(30f) // Sets the tilt of the camera to 30 degrees
                .build() // Creates a CameraPosition from the builder

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        isAppRunning=false
    }
    private fun recursiveOperation(){
        Handler(Looper.getMainLooper()).postDelayed({
            if (!latitudeTextView.isNullOrEmpty() && !longitTextView.isNullOrEmpty()) {
                val coordinate = LatLng(latitudeTextView!!.toDouble(), longitTextView!!.toDouble())
                val yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 5f)
                mMap.animateCamera(yourLocation)
                val cameraPosition = CameraPosition.Builder()
                    .target(coordinate) // Sets the center of the map to Mountain View
                    .zoom(17f) // Sets the zoom
                    .bearing(90f) // Sets the orientation of the camera to east
                    .tilt(30f) // Sets the tilt of the camera to 30 degrees
                    .build() // Creates a CameraPosition from the builder

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }else{
                recursiveOperation()
            }
        },200)
    }

    private fun openDeletePopup(view: View,latlong: LatLng,name:String) {

        val inflate = PopupBinding.inflate(LayoutInflater.from(context))

        val popUp = PopupWindow(
            inflate.root, LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT, false
        )
        inflate.latlongName.text=name
        popUp.isTouchable = true
        popUp.isFocusable = true
        popUp.isOutsideTouchable = true
        popUp.showAsDropDown(view, -65, -10)
    }

    private fun onClick() {

        bindingMap.btnConfirmLocation.setOnClickListener {
            if (bindingMap.tvMapLocation.text == "UnKnown Location...") {
                Toast.makeText(requireActivity(),"Please pin to the valid location",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
                val appPrefs = AppPref(requireActivity())
                val shopsFragment = ShopsFragment()
                Log.d("pass", "" + passData)
                appPrefs.setValue("location", passData ?: "").toString()
                (activity as MainActivity).replaceFragment(shopsFragment, false, false)
            }
    }
    fun getAddress(lat: Double, lng: Double) :String{
        var name:String=""
        var obj: Address? =null
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
          //  val addresses: List<Address> = geocoder.getFromLocationName(lat, lng, 1)
            if (addresses.size>0) {
                 obj = addresses[0]

            val add: String = obj!!.getAddressLine(0)
            Log.v("IGA", "Address$add")
            Log.v("IGA New", "Address ${"subAdminArea: "}${obj.subAdminArea}" +
                    ",${"thoroughfare: "}${obj.thoroughfare}" +
                    ",${"subThoroughfare: "}${obj.subThoroughfare}" +
                    ",${"adminArea: "}${obj.adminArea}" +
                    ",${"featureName: "}${obj.featureName}" +
                    ",${"locality: "}${obj.locality}" +
                    ",${"subLocality: "}${obj.subLocality}," +
                    "${"premises: "}${obj.premises}")

             name=add

        /*        name= if (obj.locality!=null){
                    obj.locality
                }else{
                    name
                }*/
            }else{
            name="${"UnKnown Location..."}"
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return name
    }

    fun getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                mFusedLocationClient!!.lastLocation.addOnCompleteListener { task ->
                    val convertBtm = drawableToBitmap(resources.getDrawable(R.drawable.resize_location_icon))
                    val location = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        if (!this::mMap.isInitialized){
                            return@addOnCompleteListener
                        }
                        val sydney = LatLng(location.latitude, location.longitude)
//                        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in India"))?.setIcon(BitmapDescriptorFactory.fromBitmap(convertBtm!!))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                        latitudeTextView= location.latitude.toString() + ""
                        longitTextView = location.longitude.toString() + ""
                        mMap.setOnCameraIdleListener(OnCameraIdleListener { //get latlng at the center by calling
                            val midLatLng: LatLng = mMap.cameraPosition.target
                            bindingMap.tvMapLocation.text=getAddress(midLatLng.latitude,midLatLng.longitude)
                            passData=getAddress(midLatLng.latitude,midLatLng.longitude)


                            bindingMap.curr.setOnClickListener {
                                openDeletePopup(bindingMap.curr,midLatLng,getAddress(midLatLng.latitude,midLatLng.longitude))
                             //   bindingMap.tvMapLocation.text=getAddress(midLatLng.latitude,midLatLng.longitude)
                            }
                        })
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
    private fun drawableToBitmap(drawable: Drawable): Bitmap? {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
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
            latitudeTextView= "Latitude: " + mLastLocation.latitude.toString() + ""
            longitTextView = "Longitude: " + mLastLocation.longitude.toString() + ""
        }
    }

    // method to check for permissions
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
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID
        )
    }

    // method to check
    // if location is enabled
    private fun isLocationEnabled(): Boolean {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // If everything is alright then
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            getLastLocation()
        }
    }

}