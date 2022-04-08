package com.netSet.urbanstores.ui.shops.ShopProducts.allProducts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.R
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.databinding.FragmentAllProductsBinding
import com.netSet.urbanstores.models.AllProductListModel
import com.netSet.urbanstores.models.ShowStoreList
import com.netSet.urbanstores.network.ApiService
import com.netSet.urbanstores.network.Repositories
import com.netSet.urbanstores.network.viewmodel.MainViewModel
import com.netSet.urbanstores.network.viewmodel.MainViewModelFactory
import com.netSet.urbanstores.room.CartEntity
import com.netSet.urbanstores.room.RoomDbViewModel
import com.netSet.urbanstores.utills.Validation
import com.netSet.urbanstores.utills.Validation.notVisible
import com.netSet.urbanstores.utills.Validation.visible
import kotlinx.android.synthetic.main.fragment_all_products.*

class AllProductsFrag(var receiveID: String?) : BaseFragment() {

    var binding: FragmentAllProductsBinding? = null
    var adapter: AllproductsAdapter? = null
    lateinit var layoutManager: LinearLayoutManager

    lateinit var retro: ApiService
    lateinit var repo: Repositories
    lateinit var viewmodel: MainViewModel
    lateinit var list: AllProductListModel
    var filteredList: ArrayList<AllProductListModel.Datum> = ArrayList()
    lateinit var roomDbViewModel: RoomDbViewModel
    // var list2 : ArrayList<AllProductListModel.Datum> = ArrayList()

    var pageno = 1
    var iscrolled = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_all_products, container, false)
            rootView = binding?.root
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.noDataTv?.notVisible()


    }

    private fun setObserver() {

        /**This observer is used for set the shimmer*/
        viewmodel.showShimmer.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding?.shimmerLayout?.visible()
                binding?.allProductsRecycler?.notVisible()

            } else {
                binding?.shimmerLayout?.notVisible()
                binding?.allProductsRecycler?.visible()

            }
        })


        viewmodel.allProductListModel.observe(viewLifecycleOwner, Observer {


            if (it != null) {
                list = it
                // it.body?.data?.let { it1 -> filteredList.addAll(it1) }
                adapter?.notifyDataSetChanged()
                filteredList = it.body?.data!!
                allProductsAdapterCall()

                if (filteredList.size <= 0) {
                    binding?.allProductsRecycler?.notVisible()

                    binding?.noDataTv?.visible()


                }
            }

        })
    }

    private fun allProductsAdapterCall() {
        if (getBaseActivity().tabPosition == 0) {

            adapter = AllproductsAdapter(this, filteredList, getBaseActivity())
            Log.e("allProductsAdapterCall", "allProductsAdapterCall: ${filteredList.size}")


        } else {


            //  Toast.makeText(context, getBaseActivity().tabPosition.toString(),Toast.LENGTH_LONG).show()
            this.filteredList = (list.body?.data?.filter {
                it.categoryId!! == list.productCategories?.get(getBaseActivity().tabPosition )?.id!!
            } as ArrayList<AllProductListModel.Datum>?)!!
            adapter = AllproductsAdapter(this, filteredList, getBaseActivity())

            Log.e(
                "allProductsAdapterCall",
                "allProductsAdapterCall else part: ${filteredList.size}",
            )
        }
        layoutManager = LinearLayoutManager(context)
        binding?.allProductsRecycler?.setHasFixedSize(true)
        binding?.allProductsRecycler?.layoutManager = layoutManager
        binding?.allProductsRecycler?.adapter = adapter

        adapter?.notifyDataSetChanged()

    }

    fun addtocart(productId: Int) {

        for (i in 0 until getBaseActivity().shopProductsList.size) {
            if (productId == getBaseActivity().shopProductsList[i].id) {
                getBaseActivity().shopProductsList.get(i).isAddedToCart = true
                getBaseActivity().itemCounts++
                adapter?.notifyDataSetChanged()
            }
        }
    }

    fun removefromcart(productId: Int) {
        for (i in 0 until getBaseActivity().shopProductsList.size) {
            if (productId == getBaseActivity().shopProductsList[i].id) {
                getBaseActivity().shopProductsList.get(i).isAddedToCart = false
                getBaseActivity().itemCounts--
                adapter?.notifyDataSetChanged()
            }
        }
    }


    private fun initiateViewModel() {
        viewmodel =
            ViewModelProvider(requireActivity(), MainViewModelFactory(activity as MainActivity))
                .get(MainViewModel::class.java)

        roomDbViewModel =
            ViewModelProvider(this, MainViewModelFactory(activity as MainActivity)).get(
                RoomDbViewModel::class.java
            )
    }

    override fun onResume() {
        super.onResume()

        initiateViewModel()
        setObserver()
        allProductsAdapterCall()

        /**/


        // ApplyPagination()

        /** Comment Api Call Fror call api again and again*/
        //  viewmodel.callAllProductListRes(receiveID.toString(),"",pageno.toString())

//          setObserver()


    }

    fun addProductsInDb(position: Int, spinerPos : Int) {

        val list = filteredList
        val productName = list?.get(position)?.productName
        val productId = list?.get(position)?.id
        val productsQuantity = spinerPos.toString()
        val productPrice = (list?.get(position)?.price.toString())
        val categoryId = (list.get(position).categoryId.toString())
        //  val totalPrice= (productPrice.toInt() * productsQuantity.toInt()).toString()
        val imageUrl = list?.get(position)?.imageUrl.toString()


        val cartEntity =
            CartEntity(productId,categoryId, productsQuantity, productPrice, productName, true, imageUrl)
        Toast.makeText(context, list?.get(position)?.id.toString(), Toast.LENGTH_LONG).show()

        filteredList[position].isAddedToCart = true

        adapter?.notifyDataSetChanged()
        //add data to database

        roomDbViewModel.insertProduct(cartEntity)
        Toast.makeText(context, "Successfully added to cart", Toast.LENGTH_SHORT).show()

        //  Toast.makeText(context,list?.get(position)?.isAddedToCart.toString(),Toast.LENGTH_LONG).show()
        // Toast.makeText(context,list.body?.data?.get(position)?.productsQuantity.toString(),Toast.LENGTH_LONG).show()
    }


    private fun ApplyPagination() {

        /** For Pagination */

        binding?.allProductsRecycler?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    iscrolled = true


            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //for visible items on screen
                val vitem = layoutManager.childCount
                //for scrooled out items
                val skipped = layoutManager.findFirstVisibleItemPosition()
                //for total items
                val totalitem = binding?.allProductsRecycler?.layoutManager?.itemCount


                //if visible item count and skipped item count is equals to total item count then we fetch new data
                if (iscrolled && vitem + skipped == totalitem) {

                    // binding.progressbar2.visibility=View.VISIBLE
                    viewmodel.callAllProductListRes(receiveID.toString(), "", pageno.toString())
                    pageno++

                    Log.d("TAG", "onScrolled: " + pageno.toString())
                    //  Log.d("TAG", "onScrolled: ")
                    iscrolled = false
                }
            }
        })
    }

    fun inCreaseQuantity(position: Int) {
        if(!filteredList[position].isAddedToCart) {
            filteredList[position].isAddedToCart = true
        }


        if(filteredList?.get(position)?.productsQuantity == null  || filteredList?.get(position)?.productsQuantity == 0) {
            filteredList?.get(position)?.productsQuantity = 1
        }

        adapter?.notifyDataSetChanged()
    }

    fun addQuantity(position: Int) {
        filteredList?.get(position)?.productsQuantity = filteredList.get(position).productsQuantity!! + 1
        adapter?.notifyDataSetChanged()
    }

    fun removeQuantity(position: Int) {
        filteredList?.get(position)?.productsQuantity = filteredList.get(position).productsQuantity!! - 1

        if (filteredList?.get(position)?.productsQuantity!! < 1){
            filteredList[position].isAddedToCart = false
        }

        adapter?.notifyDataSetChanged()
    }
}