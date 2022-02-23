package com.netSet.urbanstores.ui.shops.ShopProducts

interface CartCallback {
    fun addtocart(position: Int, selectedItem: Int)
    fun removefromcart(position : Int)
}