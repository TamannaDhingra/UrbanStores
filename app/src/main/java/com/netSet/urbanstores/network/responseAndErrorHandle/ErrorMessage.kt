package com.netSet.urbanstores.network.responseAndErrorHandle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ErrorMessage : Serializable {

    @SerializedName("message")
    @Expose
    var message: String? = null
}