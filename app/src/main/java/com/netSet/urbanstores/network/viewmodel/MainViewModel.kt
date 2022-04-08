package com.netSet.urbanstores.network.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.base.BaseFragment
import com.netSet.urbanstores.models.*
import com.netSet.urbanstores.network.ApiResponse
import com.netSet.urbanstores.network.RetroBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MainViewModel(val mainActivity: MainActivity) : ViewModel(), ApiResponse,
    Callback<JsonElement> {

    var callStoreList : Call<JsonElement> ?= null
    var callRegisterUser : Call<JsonElement> ?=null
    var callAllProductList : Call<JsonElement> ?=null
    var callUserProfileDetails: Call<JsonElement>?=null
    var callUpdateProfileDetails:Call<JsonElement>?=null


    val showShimmer : MutableLiveData<Boolean> = MutableLiveData()
    val storeListModel : MutableLiveData<ShowStoreList> = MutableLiveData()
    val registerUserModel : MutableLiveData<RegisterUserResponse> = MutableLiveData()
    val allProductListModel:MutableLiveData<AllProductListModel> = MutableLiveData()
    val UserProfileDetails : MutableLiveData<GetUserProfileDetails> = MutableLiveData()
    val UpdateProfileDetails : MutableLiveData<UpdateProfileDetailResponse> = MutableLiveData()


    override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
     //  mainActivity.hideLoader()

        if(response.isSuccessful) {
            onSuccess(call, response.code(), response.body().toString())
        }
        else {
            onError(call, response.code(), response.errorBody().toString())


        }
    }

    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
        Toast.makeText(mainActivity, t.message, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(call: Call<JsonElement>, responseCode: Int, response: String) {

        this.showShimmer.value=false
        mainActivity.hideSpinkit()
        if(call == callStoreList) {
            storeListModel.value = Gson().fromJson(response, ShowStoreList::class.java)
        }

        else if (call == callRegisterUser){
            registerUserModel.value =  Gson().fromJson(response, RegisterUserResponse::class.java)
        }

        else if (call == callAllProductList){
            allProductListModel.value= Gson().fromJson(response,AllProductListModel::class.java)
        }

        else if (call == callUserProfileDetails){
            UserProfileDetails.value=Gson().fromJson(response,GetUserProfileDetails::class.java)
        }

        else if (call == callUpdateProfileDetails){
            UpdateProfileDetails.value=Gson().fromJson(response,UpdateProfileDetailResponse::class.java)
        }
    }

    override fun onError(call: Call<JsonElement>, errorCode: Int, errorMsg: String) {

    }

    fun callStoreListRes(lat:String,lng:String,search:String,page:String) {

        callStoreList = RetroBuilder.service.getStoreList(lat, lng, search,page)
        hitApi(callStoreList, true, mainActivity, this)

    }


    fun callAllProductListRes(id:String,search: String,page:String){
        callAllProductList = RetroBuilder.service.getProductList(id,search,page)
        hitApi(callAllProductList,true,mainActivity,this)
    }

    fun callRegisterUserRes(phone_code:String, phone_number:String){

        val jsonObject = JSONObject()
        jsonObject.put("phone_code", phone_code)
        jsonObject.put("phone_number", phone_number)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        callRegisterUser = RetroBuilder.service.RegisterUser(requestBody)
        hitApi(callRegisterUser,false,mainActivity,this)
    }

    fun callUserProfileDetailsRes(phone_number:String) {
        callUserProfileDetails=RetroBuilder.service.getuserProfileDetails(phone_number)
        hitApi(callUserProfileDetails,false,mainActivity,this)
    }

    fun callUpdateUserProfileDetailRes(phone_number:String,name:String,email:String,profile_image:String,address:String){

        val context = HashMap<String, RequestBody>()
        context.put("phone_number", getTextRequestBodyParams(phone_number))
        context.put("name", getTextRequestBodyParams(name))
        context.put("email", getTextRequestBodyParams(email))
        context.put("profile_image\"; filename=\""+File(profile_image.toUri().path).absolutePath, getFileRequestBodyParams(File(profile_image.toUri().path)))
        context.put("address", getTextRequestBodyParams(address))

        callUpdateProfileDetails = RetroBuilder.service.updateUserProfile(context)
        hitApi(callUpdateProfileDetails,false,mainActivity,this)
    }

    fun getFileRequestBodyParams(value: File): RequestBody {
        return value.asRequestBody("image/jpeg".toMediaTypeOrNull())
    }

    fun getTextRequestBodyParams(value: String): RequestBody {
        return value.toRequestBody("text/form-data".toMediaTypeOrNull())
    }




    fun hitApi(call: Call<JsonElement>?, showProgress: Boolean, context: Context, listener: ApiResponse) {
        this.showShimmer.value = showProgress
        if(showProgress){
           mainActivity.showSpinkit()
       }

        call?.enqueue(this)
    }
}