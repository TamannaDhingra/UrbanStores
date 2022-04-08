package com.netSet.urbanstores.network

import com.google.gson.JsonElement
import retrofit2.Call

interface ApiResponse {


    fun onSuccess(call: Call<JsonElement>, responseCode: Int, response: String)
    fun onError(call: Call<JsonElement>, errorCode: Int, errorMsg: String)

}