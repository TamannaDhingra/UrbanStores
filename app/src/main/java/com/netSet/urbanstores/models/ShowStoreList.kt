package com.netSet.urbanstores.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import com.netSet.urbanstores.models.AllProductListModel.Datum


class ShowStoreList {
    @SerializedName("body")
    @Expose
    var body: StoreList? = null

    @SerializedName("total_number_pages")
    @Expose
    var totalNumberPages: Int = 0


    class StoreList {
        @SerializedName("current_page")
        @Expose
        var currentPage: Int? = null

        @SerializedName("data")
        @Expose
        var data: ArrayList<Data>? = null

        @SerializedName("first_page_url")
        @Expose
        var firstPageUrl: String? = null

        @SerializedName("from")
        @Expose
        var from: Int? = null

        @SerializedName("last_page")
        @Expose
        var lastPage: Int? = null

        @SerializedName("last_page_url")
        @Expose
        var lastPageUrl: String? = null

        @SerializedName("links")
        @Expose
        var links: List<Link>? = null

        @SerializedName("next_page_url")
        @Expose
        var nextPageUrl: String? = null

        @SerializedName("path")
        @Expose
        var path: String? = null

        @SerializedName("per_page")
        @Expose
        var perPage: Int? = null

        @SerializedName("prev_page_url")
        @Expose
        var prevPageUrl: Any? = null

        @SerializedName("to")
        @Expose
        var to: Int? = null

        @SerializedName("total")
        @Expose
        var total: Int? = null
    }

    class Data {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("store_name")
        @Expose
        var storeName: String? = null

        @SerializedName("phone_number")
        @Expose
        var phoneNumber: String? = null

        @SerializedName("email")
        @Expose
        var email: Any? = null

        @SerializedName("store_image")
        @Expose
        var storeImage: String? = null

        @SerializedName("provider_image")
        @Expose
        var providerImage: Any? = null

        @SerializedName("address")
        @Expose
        var address: String? = null

        @SerializedName("address_latitude")
        @Expose
        var addressLatitude: Any? = null

        @SerializedName("address_longitude")
        @Expose
        var addressLongitude: Any? = null

        @SerializedName("notification")
        @Expose
        var notification: Any? = null

        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("password")
        @Expose
        var password: String? = null

        @SerializedName("suspend")
        @Expose
        var suspend: Int? = null

        @SerializedName("store_open_time")
        @Expose
        var storeOpenTime: Any? = null

        @SerializedName("store_close_time")
        @Expose
        var storeCloseTime: Any? = null

        @SerializedName("home_delivery")
        @Expose
        var homeDelivery: Any? = null

        @SerializedName("mrp")
        @Expose
        var mrp: Any? = null

        @SerializedName("image_url")
        @Expose
        var imageUrl: String? = null
    }

    class Link {
        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("label")
        @Expose
        var label: String? = null

        @SerializedName("active")
        @Expose
        var active: Boolean? = null
    }
}