package com.netSet.urbanstores.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiResponseModel<T>(
    @SerializedName("code")
    var code: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("token_type")
    var token_type: T?
) : Serializable