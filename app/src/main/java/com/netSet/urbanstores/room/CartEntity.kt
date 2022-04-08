package com.netSet.urbanstores.room

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_table")
data class CartEntity (

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "categoryId")
    var categoryId: String? = null,


    @ColumnInfo(name = "productsQuantity")
    var productsQuantity: String? = null,

   /* @ColumnInfo(name = "productCategory")
    var productCategory: String? = null,
   */

    @ColumnInfo(name = "productPrice")
    var productPrice: String? = null,

    @ColumnInfo(name = "productname")
    var productname: String? = null,

    @ColumnInfo(name = "isAddedToCart")
    var isAddedToCart: Boolean? = false,

    @ColumnInfo(name = "image_url")
    var image_url:String?= null


)
