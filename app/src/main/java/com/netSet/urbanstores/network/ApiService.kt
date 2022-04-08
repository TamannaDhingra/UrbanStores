package com.netSet.urbanstores.network

import com.google.gson.JsonElement
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @POST("registered_user")
    fun RegisterUser(@Body rqstbody: RequestBody):Call<JsonElement>

    @GET("show_store_list/")
    fun getStoreList(@Query("lat") lat:String ,
                     @Query("lng") lng:String ,
                     @Query("search") search:String,
                     @Query("page")page:String):Call<JsonElement>

    @GET("product_list/{id}")
    fun getProductList(@Path("id") id:String,
                       @Query("search")search: String,
                       @Query("page")page:String):Call<JsonElement>


    @GET("user_profile")
    fun getuserProfileDetails(@Query("phone_number") id:String):Call<JsonElement>

    @Multipart
    @POST("update_user_profile")
    fun updateUserProfile(@PartMap params: HashMap<String, RequestBody>): Call<JsonElement>

    @POST("get_product_by_ids")
    fun getProductByIds()
}