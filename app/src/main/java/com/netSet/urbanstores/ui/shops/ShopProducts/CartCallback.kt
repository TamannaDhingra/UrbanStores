package com.netSet.urbanstores.ui.shops.ShopProducts

interface CartCallback {
    fun addtocart(position: Int, selectedItem: Int, selectedItemPosition: Int)
    fun removefromcart(position: Int, position1: Int)
}