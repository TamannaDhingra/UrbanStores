package com.netSet.urbanstores.network.responseAndErrorHandle

import com.google.gson.JsonElement
import retrofit2.Call

interface ApiService {

    fun onSuccess(call : Call<JsonElement>, responeCode : Int,response :String)
    fun onError(call : Call<JsonElement>,errorCode : Int,errorMsg : String)

}