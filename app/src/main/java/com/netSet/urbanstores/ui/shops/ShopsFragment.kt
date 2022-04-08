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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devs.readmoreoption.ReadMoreOption
import com.google.android.gms.location.*
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentShopsBinding
import com.netSet.urbanstores.models.ShowStoreList
import com.netSet.urbanstores.network.ApiService
import com.netSet.urbanstores.network.Repositories
import com.netSet.urbanstores.network.RetroBuilder
import com.netSet.urbanstores.network.viewmodel.*
import com.netSet.urbanstores.sharePreference.AppPref
import com.netSet.urbanstores.utills.Validation
import com.netSet.urbanstores.utills.Validation.notVisible
import com.netSet.urbanstores.utills.Validation.visible


class ShopsFragment : BaseFragment() {
    private var readMoreOption: ReadMoreOption? =null
    var PERMISSION_ID = 44
    var mFusedLocationClient: FusedLocationProviderClient? = null
    var binding : FragmentShopsBinding ?=null
    lateinit var list:ShowStoreList
    var list1 : ArrayList<ShowStoreList> = ArrayList()
    var list2 : ArrayList<ShowStoreList.Data> = ArrayList()

    lateinit var  layoutManager:LinearLayoutManager
    lateinit var recyclerAdapter:ShopListsAdapter

    lateinit var retro: ApiService
    lateinit var repo: Repositories
    lateinit var viewmodel: MainViewModel
    var mLastLocation:Location ?= null

    var pageno=1
    var iscrolled=false
    val limit=4


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_shops,container,false)
        rootView = binding?.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appPrefs = AppPref(requireActivity())
        RetroBuilder.authToken = appPrefs.getToken()
        pageno=1
        binding?.expendTxt?.setOnClickListener {
            if (binding?.currentLocation?.lineCount ==1){
                binding?.currentLocation?.maxLines = 3
            }else{
                binding?.currentLocation?.maxLines = 1
            }
        }

        retro = RetroBuilder.service
        repo = Repositories(retro, activity)

        initiateViewModel()
        viewmodel.callStoreListRes("","","",pageno.toString())

        setObserver()
        setStoreList()


        /** For Pagination */

        binding?.shopsRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    iscrolled=true
                }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //for visible items on screen
                val vitem=layoutManager.childCount
                //for scrooled out items
                val skipped=layoutManager.findFirstVisibleItemPosition()
                //for total items
                val totalitem=binding?.shopsRecyclerView?.layoutManager?.itemCount


                //if visible item count and skipped item count is equals to total item count then we fetch new data
                if (iscrolled && vitem+skipped==totalitem) {

                    // binding.progressbar2.visibility=View.VISIBLE

                    if(pageno <= list.totalNumberPages){
                        pageno++
                        binding?.noStoreAvailTxtview?.notVisible()
                        viewmodel.callStoreListRes("","",binding?.searchProducts?.text.toString(),pageno.toString())

                    }
                    else{
                        Toast.makeText(context,"There is no Store Available",Toast.LENGTH_SHORT).show()
                    }
                 iscrolled=false
                }


            }
        })



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


        showBottomNavigation()
//        navigationBgVisiblity()
        counterVisible()
        val image = AppPref(requireContext()).getUserImage()
        Log.d("TAG", "onViewCreated: " +image.toString())

        setToolBar(image,"HOME",R.mipmap.cart_3x)

    }

    private fun setObserver() {

        viewmodel.storeListModel.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
           // binding?.spinKit?.visibility=View.GONE

            if (viewmodel.storeListModel.value!=null) {


                list = it
                if (binding?.searchProducts?.text?.isNotEmpty()!!) {
                    list2.clear()
                    pageno = 1

                }

                if (it != null) {

                    it.body?.data?.let { it1 ->
                        list2.addAll(it1)
                        //Toast.makeText(context, list2.size.toString(), Toast.LENGTH_SHORT).show()
                    }

                    recyclerAdapter.notifyDataSetChanged()

                    binding?.currentLocation?.setOnClickListener {
                        val locationBottomSheet = LocationBottomSheet()
                        locationBottomSheet.isCancelable = true
                        locationBottomSheet.show(
                            requireActivity().supportFragmentManager,
                            "BottomSheetLocation"
                        )
                    }

                    if (list2.size <= 0) {
                        binding?.shopsRecyclerView?.notVisible()
                        binding?.noStoreAvailTxtview?.visible()
                    } else {
                        binding?.shopsRecyclerView?.visible()
                        binding?.noStoreAvailTxtview?.notVisible()
                    }

                    Log.d("setObserver", "setObserver: ShopsFragment " + list2.size.toString())


                } else {
                    Toast.makeText(
                        requireContext(),
                        "there is no list available",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                viewmodel.storeListModel.value=null
            }
        })

    }

    private fun setStoreList() {

        recyclerAdapter = ShopListsAdapter(this, list2)
        layoutManager = LinearLayoutManager(context)
        binding?.shopsRecyclerView?.setHasFixedSize(true)
        binding?.shopsRecyclerView?.layoutManager = layoutManager
        binding?.shopsRecyclerView?.adapter = recyclerAdapter

    }

    private fun initiateViewModel() {
        viewmodel = ViewModelProvider(this,MainViewModelFactory(activity as MainActivity))
            .get(MainViewModel::class.java)

    }



    private fun searchTxtWatcher() {
        binding?.searchProducts?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               // viewmodel.callStoreListRes()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //viewmodel.callStoreListRes()
                //Toast.makeText(requireActivity(),mLastLocation.longitude.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun afterTextChanged(p0: Editable?) {


                if (p0.toString().startsWith(" "))
                    binding?.searchProducts?.setText("")

                if(binding?.searchProducts?.text.toString().isEmpty()) {
                    list2.clear()
                }
                viewmodel.callStoreListRes("","",p0.toString(),"1")

            }
        })
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
                        binding?.currentLocation?.text=Validation.getAddress(location.latitude,location.longitude, mainActivity!!)
                        viewmodel.callStoreListRes(location.latitude.toString(),location.longitude.toString(),"",pageno.toString())
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
             mLastLocation = locationResult.lastLocation
            binding?.currentLocation?.text=Validation.getAddress(mLastLocation?.latitude!!,mLastLocation?.longitude!!,mainActivity!!)
            //Toast.makeText(context, mLastLocation?.latitude.toString(), Toast.LENGTH_LONG).show()
            viewmodel.callStoreListRes(mLastLocation?.latitude.toString(),mLastLocation?.longitude.toString(),"",pageno.toString())
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

    override fun onResume() {
        super.onResume()
        binding?.noStoreAvailTxtview?.notVisible()
        list2.clear()
    }

}