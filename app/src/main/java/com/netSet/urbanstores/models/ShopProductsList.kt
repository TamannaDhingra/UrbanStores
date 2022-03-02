package com.netSet.urbanstores.models

data class ShopProductsList(
    val id : Int,
    val productImg: Int,
    val productname: String,
    val productPrice: Int,
    val discount: Int,
    var isAddedToCart : Boolean,
    val productCategory : String,
    var productPcs : Int
)