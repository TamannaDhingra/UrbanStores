package com.netSet.urbanstores.ui.orders.orderDetails

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentOrderDetailsBinding
import com.netSet.urbanstores.models.ModelClssTrackOrder

import com.netSet.urbanstores.ui.orders.OrderDetailsInfo.FragmentOrderDetailsInfo
import de.hdodenhof.circleimageview.CircleImageView

class FragmentOrderDetails : BaseFragment(), GoogleMap.OnMarkerClickListener {
    lateinit var binding: FragmentOrderDetailsBinding
    val arrayList = ArrayList<ModelClssTrackOrder>()

    /** Code for map fragment*/
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var lastLocation: Location
    lateinit var mMap: GoogleMap

    private val LOCATION_REQUEST_CODE = 1


    private val callback = OnMapReadyCallback { googleMap ->


        mMap = googleMap
        googleMap.uiSettings.isZoomControlsEnabled=true
        googleMap. setOnMarkerClickListener(this)
        setUpMap()


    }

    /**above code for map*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_order_details,container,false)

        navigationBgVisiblity()
        setToolBar(R.mipmap.back_48x48,"ORDER DETAILS",0)
        arrayList.add(ModelClssTrackOrder("Order Placed","2 june 2020 at 7:54 PM",R.mipmap.check))
        arrayList.add(ModelClssTrackOrder("Order Accepted","2 june 2020 at 7:54 PM",R.mipmap.check))
        arrayList.add(ModelClssTrackOrder("Order Packed","2 june 2020 at 7:54 PM",R.mipmap.check))
        arrayList.add(ModelClssTrackOrder("On The Way","2 june 2020 at 7:54 PM",R.mipmap.check))
        arrayList.add(ModelClssTrackOrder("Delivered","2 june 2020 at 7:54 PM",R.mipmap.check))

        val recyclerAdapter = AdapterOrderDetails(arrayList, requireContext())
        binding.rvTrackOrder.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTrackOrder.adapter = recyclerAdapter


        /**map fragment*/
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapContainer) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(requireContext())

        binding.cardView.setOnClickListener {
            getBaseActivity().replaceFragment(FragmentOrderDetailsInfo(),true,false)
        }
        hideBottomNavigation()





        binding.cardView.setOnClickListener {
            getBaseActivity().replaceFragment(FragmentOrderDetailsInfo(),true,false)
        }

        return binding.root
    }



    /** Below Code for map fragment with fused location */

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            if(it!=null){
                lastLocation=it
                val currentLatLong= LatLng(it.latitude,it.longitude)
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,10f))
            }
        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        // val bitmapToDrawable= drawableToBitmap(resources.getDrawable(R.drawable.resize_image))
        val markerOptions= MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")

        mMap.addMarker(markerOptions)?.setIcon(
            BitmapDescriptorFactory.fromBitmap(
                createCustomMarker(requireContext(), R.drawable.dhabaimg1)!!
            )
        )

        //  mMap.addMarker(markerOptions)?.setIcon(BitmapDescriptorFactory.fromBitmap(bitmapToDrawable!!))
    }

    fun createCustomMarker(context: Context, @DrawableRes resource: Int): Bitmap? {
        val marker: View =
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.custom_marker, null)

        //   loc_img.setImageResource(resource)
        val markerImage: CircleImageView =
            marker.findViewById<View>(R.id.loc_img) as CircleImageView
        markerImage.setImageResource(resource)

        /*  val txt_name = marker.findViewById<View>(R.id.name) as TextView
          txt_name.text = _name*/

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        marker.layoutParams = ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT)
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        marker.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(
            marker.measuredWidth,
            marker.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        marker.draw(canvas)
        return bitmap
    }

    override fun onMarkerClick(p0: Marker): Boolean =false

}
