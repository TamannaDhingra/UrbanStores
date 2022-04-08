package com.netSet.urbanstores.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(
    @SerializedName("body")
    var body: RegisterUser,
    @SerializedName("code")
    var code: Int,
    @SerializedName("message")
    var message: String
)

data class RegisterUser(
    @SerializedName("id")

    var id: Int? = null,

    @SerializedName("name")
  var name: String? = null,

@SerializedName("email")
@Expose
var email: String? = null,

@SerializedName("email_verified_at")
@Expose
var emailVerifiedAt: Any? = null,

@SerializedName("created_at")
@Expose
var createdAt: String? = null,

@SerializedName("updated_at")
@Expose
var updatedAt: String? = null,

@SerializedName("profile_image")
@Expose
var profileImage: String? = null,

@SerializedName("phone_number")
@Expose
var phoneNumber: String? = null,

@SerializedName("otp")
@Expose
var otp: Any? = null,

@SerializedName("mobile_verification")
@Expose
var mobileVerification: Any? = null,

@SerializedName("address")
@Expose
var address: String? = null,

@SerializedName("current_longitude")
@Expose
var currentLongitude: Any? = null,

@SerializedName("current_latitude")
@Expose
var currentLatitude: Any? = null,

@SerializedName("notification_status")
@Expose
var notificationStatus: Any? = null,

@SerializedName("status")
@Expose
var status: Any? = null,

@SerializedName("date_formate")
@Expose
var dateFormate: Any? = null,

@SerializedName("suspend")
@Expose
var suspend: Int? = null,

@SerializedName("phone_code")
@Expose
var phoneCode: String? = null,

@SerializedName("token")
@Expose
var token: String? = null,

@SerializedName("image_url")
@Expose
var imageUrl: String? = null,
)
