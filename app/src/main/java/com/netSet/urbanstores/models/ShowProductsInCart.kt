package com.netSet.urbanstores.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.netSet.urbanstores.models.AllProductListModel.Datum


class ShowProductsInCart {
    @SerializedName("code")
    @Expose
    var code: Int? = null

    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null


    class Datum {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("product_id")
        @Expose
        var productId: Int? = null

        @SerializedName("user_id")
        @Expose
        var userId: Int? = null

        @SerializedName("product_quantity")
        @Expose
        var productQuantity: Int? = null

        @SerializedName("price")
        @Expose
        var price: Int? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }
}