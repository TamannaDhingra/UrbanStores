package com.netSet.urbanstores.ui.shops.ShopProducts

import ShopDiscountsAdapters
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentShopProductsBinding
import com.netSet.urbanstores.models.AllProductListModel
import com.netSet.urbanstores.network.viewmodel.MainViewModel
import com.netSet.urbanstores.network.viewmodel.MainViewModelFactory
import com.netSet.urbanstores.ui.shops.ShopProducts.allProducts.AllProductsFrag
import com.netSet.urbanstores.utills.Validation.notVisible
import com.netSet.urbanstores.utills.Validation.visible

class ShopProductsFrag(val receiveID: String) : BaseFragment(), TabLayout.OnTabSelectedListener {

    var binding : FragmentShopProductsBinding ?=null
    var adapter : ShopDiscountsAdapters ?=null
    var productsTabAdapter : ProductsTabAdapter ?= null
    lateinit var prodList:AllProductListModel
    lateinit var viewmodel: MainViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_products, container, false)
            rootView = binding?.root
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // discountsAdapterCall()
         hideBottomNavigation()
         counterVisible()
         setTextWatcher()
         setToolBar("back","SHOP NAME",R.mipmap.cart_3x)
         initiateViewModel()
        setObserver()

        viewmodel.callAllProductListRes(receiveID,"","")

    }


    private fun setupViewPager() {

        productsTabAdapter = ProductsTabAdapter(this)
        prodList?.productCategories?.map {
            productsTabAdapter?.addFragment(AllProductsFrag(receiveID))
        }
       // productsTabAdapter?.addFragment(AllProductsFrag(receiveID))
       /* productsTabAdapter?.addFragment(AllProductsFrag(receiveID))
        productsTabAdapter?.addFragment(AllProductsFrag(receiveID))
        productsTabAdapter?.addFragment(AllProductsFrag(receiveID))
        productsTabAdapter?.addFragment(AllProductsFrag(receiveID))*/
        binding?.productsViewPager?.adapter = productsTabAdapter

        binding?.productsViewPager?.setSaveEnabled(false)

        binding?.productsViewPager?.clipToPadding = false
        binding?.productsViewPager?.clipChildren = false
        binding?.productsViewPager?.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding?.productsViewPager?.getChildAt(0)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding?.productsTabLayout?.addOnTabSelectedListener(this)

        TabLayoutMediator(binding?.productsTabLayout!!, binding?.productsViewPager!!) { tab, position ->
            tab.text = prodList.productCategories?.get(position)?.name?.toUpperCase()

     /*       when(position) {

                0 -> {

                }
                1-> {
                    tab.text = "Fruit"
                }
                2 -> {
                    tab.text = "Vegetable"
                }
                3 -> {
                    tab.text = "Packages"
                }

                3 -> {
                    tab.text = "Aloo"
                }
            }*/
        }.attach()
    }

    private fun discountsAdapterCall(list: AllProductListModel) {
        val viewPagerAdapter = ShopDiscountsAdapters(context,this,list, BaseActivity())
        binding?.discountProducts?.adapter = viewPagerAdapter
        viewPagerAdapter.notifyDataSetChanged()
//        Log.d("TAG", "onViewCreated: Shop Product Fragment" + list.productBanner?.get(0)?.imageUrl.toString())

    }

    private fun initiateViewModel() {
        viewmodel = ViewModelProvider(requireActivity(), MainViewModelFactory(activity as MainActivity))[MainViewModel::class.java]
    }

    private fun setObserver() {

        viewmodel.showShimmer.observe(viewLifecycleOwner, Observer {
            if(it){
                binding?.shimmerLayout?.visible()
                binding?.discountProducts?.notVisible()
            }
            else{
                binding?.shimmerLayout?.notVisible()
                binding?.discountProducts?.visible()
            }
        })

        viewmodel.allProductListModel.observe(viewLifecycleOwner) {
            if (it != null) {
                prodList = it
                setupViewPager()
                discountsAdapterCall(it)
            }
            else{
               Toast.makeText(context,"NO Data Found" ,Toast.LENGTH_SHORT).show()
            }
        }
    }
     private fun setTextWatcher(){
         binding?.searchProducts?.addTextChangedListener(object : TextWatcher{
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

             }

             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

             }

             override fun afterTextChanged(p0: Editable?) {
                 if (p0.toString().startsWith(" "))

                     binding?.searchProducts?.setText("")
                  viewmodel.callAllProductListRes(receiveID,p0.toString(),"1")
             }
         })
     }


    override fun onTabSelected(tab: TabLayout.Tab?) {
        getBaseActivity().tabPosition = tab?.position!!
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
}