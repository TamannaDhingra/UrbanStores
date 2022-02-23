package com.netSet.urbanstores.ui.maps

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.imageview.ShapeableImageView
import com.netSet.urbanstores.R
import de.hdodenhof.circleimageview.CircleImageView

class MapsFragment : Fragment(),GoogleMap.OnMarkerClickListener {

   lateinit var fusedLocationProviderClient: FusedLocationProviderClient
   lateinit var lastLocation:Location
    lateinit var mMap: GoogleMap
  // lateinit var googleMap: GoogleMap

   companion object{
      private const val LOCATION_REQUEST_CODE=1
   }

    private val callback = OnMapReadyCallback { googleMap ->


        mMap = googleMap
        googleMap.uiSettings.isZoomControlsEnabled=true
        googleMap. setOnMarkerClickListener(this)
        setUpMap()
       /* googleMap.setOnMapClickListener {
            val sydney = LatLng(-34.0, 151.0)
            googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    sydney, 10f
                )
            )
        }*/

    }


    

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                 LOCATION_REQUEST_CODE)
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            if(it!=null){
                lastLocation=it
                val currentLatLong=LatLng(it.latitude,it.longitude)
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,10f))
            }
        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
       // val bitmapToDrawable= drawableToBitmap(resources.getDrawable(R.drawable.resize_image))
       val markerOptions=MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")

        mMap.addMarker(markerOptions)?.setIcon(
            BitmapDescriptorFactory.fromBitmap(
                createCustomMarker(requireContext(), R.drawable.dhabaimg1)!!
            )
        )

      //  mMap.addMarker(markerOptions)?.setIcon(BitmapDescriptorFactory.fromBitmap(bitmapToDrawable!!))
    }
    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        val bitmap =
            Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onMarkerClick(p0: Marker): Boolean =false


}