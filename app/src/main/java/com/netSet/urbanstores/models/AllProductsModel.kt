package com.netSet.urbanstores.models

data class AllProductsModel(
    val productImg: Int,
    val productname: String,
    val productPrice: Int,
    val discount: String,
    var isAddedToCart : Boolean,
    val productCategory : String,
    var productPcs : Int

    )