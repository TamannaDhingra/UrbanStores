package com.netSet.urbanstores.ui.shops.ShopProducts.allProducts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.netSet.urbanstores.R
import com.netSet.urbanstores.base.BaseActivity
import com.netSet.urbanstores.databinding.AllproductsViewBinding
import com.netSet.urbanstores.models.AllProductListModel
import com.netSet.urbanstores.utills.Validation.notVisible
import com.netSet.urbanstores.utills.Validation.visible

class AllproductsAdapter(
    val allProductsFrag: AllProductsFrag,
    val shopProductsList: List<AllProductListModel.Datum>,
    val baseActivity: BaseActivity,
    ) : RecyclerView.Adapter<AllproductsAdapter.viewHolder>() {

    var clickedPos = -1
    var pPrice:String?=null
    var selection:String?=null

    var quantityList = arrayListOf(
        "1 Pcs",
        "2 Pcs",
        "3 Pcs",
        "4 Pcs",
        "5 Pcs",
        "6 Pcs",
        "7 Pcs",
        "8 Pcs",
        "9 Pcs",
        "10 Pcs"
    )

    inner class viewHolder(val binding: AllproductsViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = DataBindingUtil.inflate<AllproductsViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.allproducts_view, parent, false
        )
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, @SuppressLint("RecyclerView") position: Int) {


        holder.binding.itemAddButton.setOnClickListener {
          //  val spinerPos=  holder.binding.quantitys.getSelectedItemPosition() + 1
            //allProductsFrag.addProductsInDb(position)
            allProductsFrag.inCreaseQuantity(position)


        }

        holder.binding.increaseItems.setOnClickListener {
            allProductsFrag.addQuantity(position)
        }

        holder.binding.decreaseItems.setOnClickListener {
            allProductsFrag.removeQuantity(position)
        }



        if (shopProductsList.get(position).isAddedToCart){
            holder.binding.cView.visible()
            holder.binding.itemAddButton.notVisible()
        }else{
            holder.binding.cView.notVisible()
            holder.binding.itemAddButton.visible()
        }


        if (holder.binding.cView.equals(View.VISIBLE)){

           /* holder.binding.itemRemoveButton.setOnClickListener {
                allProductsFrag.removeProductsFromDb(position)
            }*/
        }

       /* holder.binding.quantitys.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selection = p0?.getItemAtPosition(p2).toString()
                    shopProductsList.get(position).productsQuantity = selection.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}

            }
*/
        holder.binding.productName.setText(shopProductsList.get(position)?.productName)
        holder.binding.productAmount.setText("Rs " +shopProductsList.get(position)?.price).toString()
        holder.binding.itemCount.setText(shopProductsList.get(position).productsQuantity.toString())
        holder.binding.quantitys.setText(shopProductsList.get(position)?.productsWeightUnit).toString()


        allProductsFrag.setImageUsingGlide(
            allProductsFrag,
            shopProductsList.get(position)?.imageUrl.toString(),
            holder.binding.productImg
        )

      /*  val adapter = ArrayAdapter(
            allProductsFrag.requireContext(),
            R.layout.quantity_spinner_layout,
            quantityList
        )
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        holder.binding.quantitys.adapter = adapter
*/

    }

    override fun getItemCount(): Int {
        return shopProductsList.size
    }
}


/**Extra Code*/


/*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
          val binding = DataBindingUtil.inflate<AllproductsViewBinding>(
              LayoutInflater.from(parent.context),
              R.layout.allproducts_view, parent, false
          )
          return viewHolder(binding)
      }*/

/*    override fun onBindViewHolder(holder: viewHolder, position: Int) {*/

//Quantity Spinner Adapter
/* val adapter = ArrayAdapter(allProductsFrag.requireContext(), R.layout.quantity_spinner_layout,quantityList)
adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
holder.binding.quantitys.adapter = adapter

holder.binding.productName.text = shopProductsList.get(position).productname
holder.proPrice.text = "Rs " + shopProductsList.get(position).productPrice.toString()
holder.proDiscount.text = "Rs "+shopProductsList.get(position).discount
if (shopProductsList.get(position).discount.equals(0)){
holder.binding.discountTxt.visibility = View.GONE
}else{
holder.discount.text = shopProductsList.get(position).discount.toString()+"%\noff"
}
//holder.noPcs.setSelection(shopProductsList.get(position).productPcs)
holder.binding.productImg.setImageResource(shopProductsList.get(position).productImg)

if (shopProductsList.get(position).isAddedToCart){
holder.removeBtn.visibility = View.VISIBLE
holder.addBtn.visibility = View.GONE
}else{
holder.removeBtn.visibility = View.GONE
holder.addBtn.visibility = View.VISIBLE
}

holder.addBtn.setOnClickListener {
allProductsFrag.addtocart(shopProductsList.get(position).id)
}

holder.removeBtn.setOnClickListener {
allProductsFrag.removefromcart(shopProductsList.get(position).id)
}*/

/* holder.noPcs.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
     override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
         val selection = p0?.getItemAtPosition(p2)
//                baseActivity.shopProductsList.get(p2).productPcs = selection.toString()
     }

     override fun onNothingSelected(p0: AdapterView<*>?) {}

 }*/

//        holder.noPcs.getSelectedItemPosition();
/*}

override fun getItemCount(): Int {
    return shopProductsList.body.size
}
}*/
