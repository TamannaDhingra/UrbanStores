package com.netSet.urbanstores.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AllProductListModel {
    @SerializedName("code")
    @Expose
    var code: Int? = null

    @SerializedName("body")
    @Expose
    var body: AllProducts? = null

    @SerializedName("total_number_product_page_count")
    @Expose
    var totalNumberProductPageCount: Int? = null

    @SerializedName("product_categories")
    @Expose
    var productCategories: List<ProductCategory>? = null

    @SerializedName("product_banner")
    @Expose
    var productBanner: List<ProductBanner>? = null


    class AllProducts {
        @SerializedName("current_page")
        @Expose
        var currentPage: Int? = null

        @SerializedName("data")
        @Expose
        var data: ArrayList<Datum>? = null

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
        var nextPageUrl: Any? = null

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

    class Datum {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("category_id")
        @Expose
        var categoryId: Int? = null

        @SerializedName("isAddedToCart")
        @Expose
        var isAddedToCart: Boolean = false

        @SerializedName("provider_id")
        @Expose
        var providerId: Int? = null

        @SerializedName("product_name")
        @Expose
        var productName: String? = null

        @SerializedName("price")
        @Expose
        var price: String? = null

        @SerializedName("description")
        @Expose
        var description: String? = null

        @SerializedName("product_image")
        @Expose
        var productImage: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("products_quantity")
        @Expose
        var productsQuantity: Int? = null

        @SerializedName("product_model")
        @Expose
        var productModel: Any? = null

        @SerializedName("products_weight")
        @Expose
        var productsWeight: Any? = null

        @SerializedName("products_weight_unit")
        @Expose
        var productsWeightUnit: String? = null

        @SerializedName("product_status")
        @Expose
        var productStatus: String? = null

        @SerializedName("products_type")
        @Expose
        var productsType: Any? = null

        @SerializedName("product_banner")
        @Expose
        var productBanner: Any? = null

        @SerializedName("mrp")
        @Expose
        var mrp: Any? = null

        @SerializedName("product_offer")
        @Expose
        var productOffer: Any? = null

        @SerializedName("image_url")
        @Expose
        var imageUrl: String? = null
    }
    class Link {
        @SerializedName("url")
        @Expose
        var url: Any? = null

        @SerializedName("label")
        @Expose
        var label: String? = null

        @SerializedName("active")
        @Expose
        var active: Boolean? = null
    }

    class ProductBanner {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("provider_id")
        @Expose
        var providerId: Int? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("image_url")
        @Expose
        var imageUrl: String? = null
    }

    class ProductCategory {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("user_type")
        @Expose
        var userType: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("provider_id")
        @Expose
        var providerId: Int? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("admin_id")
        @Expose
        var adminId: Any? = null
    }
}




/** New Model Class*/
/*

class AllProductsListModel {
    @SerializedName("code")
    @Expose
    var code: Int? = null

    @SerializedName("body")
    @Expose
    var body: Body? = null

    @SerializedName("total_number_product_page_count")
    @Expose
    var totalNumberProductPageCount: Int? = null

    @SerializedName("product_categories")
    @Expose
    var productCategories: List<ProductCategory>? = null

    @SerializedName("product_banner")
    @Expose
    var productBanner: List<ProductBanner>? = null
}

class Body {
    @SerializedName("current_page")
    @Expose
    var currentPage: Int? = null

    @SerializedName("data")
    @Expose
    var data: List<Any>? = null

    @SerializedName("first_page_url")
    @Expose
    var firstPageUrl: String? = null

    @SerializedName("from")
    @Expose
    var from: Any? = null

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
    var nextPageUrl: Any? = null

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
    var to: Any? = null

    @SerializedName("total")
    @Expose
    var total: Int? = null
}

class Link {
    @SerializedName("url")
    @Expose
    var url: Any? = null

    @SerializedName("label")
    @Expose
    var label: String? = null

    @SerializedName("active")
    @Expose
    var active: Boolean? = null
}

class ProductBanner {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("provider_id")
    @Expose
    var providerId: Int? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("image_url")
    @Expose
    var imageUrl: String? = null
}

class ProductCategory {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("user_type")
    @Expose
    var userType: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("provider_id")
    @Expose
    var providerId: Int? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("admin_id")
    @Expose
    var adminId: Any? = null
}
*/
