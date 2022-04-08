package com.netSet.urbanstores.sharePreference

import android.content.Context
import android.content.SharedPreferences

class AppPref(var ctx: Context) {

    private fun getPrefs(): SharedPreferences {
        return ctx.getSharedPreferences("preference", Context.MODE_PRIVATE)
    }
/*    private fun updatePre(): SharedPreferences {
        return ctx.getSharedPreferences("update", Context.MODE_PRIVATE)
    }*/

    fun setValue(key: String, value:String) {
        val edit = getPrefs().edit()
        edit.putString(key, value)
        edit.apply()
    }
    fun getValue(key: String) :String?{
        return getPrefs().getString(key,"")
    }

    /*fun setModelKey(key: String, value: Model?) {
        val gson = Gson()
        val edit = getPrefs().edit()
        edit.putString(key, gson.toJson(value))
        edit.apply()
    }

    fun getModelKey(key: String) :Model{
        val gson = Gson()
        val type = object  : TypeToken<String>(){}.type
        return gson.fromJson(getPrefs().getString(key,""),type)
    }

    fun setUpdate(key: String, value: Model?) {
        val gson = Gson()
        val edit = getPrefs().edit()
        edit.putString(key, gson.toJson(value))
        edit.apply()
    }

    fun getUpdate(key: String) :Model{
        val gson = Gson()
        val type = object  : TypeToken<String>(){}.type
        return gson.fromJson(getPrefs().getString(key,""),type)
    }


   *//*

    fun  delete(): Boolean {
        return getPrefs().edit().clear().commit()
    }*/
    fun  delete(): Boolean {
        return getPrefs().edit().clear().commit()
    }

    fun setUserImage(key: String, value:String) {
        val edit = getPrefs().edit()
        edit.putString(key, value)
        edit.apply()
    }
    fun getUserImage() : String? {
        return getPrefs().getString("image","")
    }

    fun setToken(value: String){
        val edit = getPrefs().edit()
        edit.putString("authToken", value)
        edit.apply()
    }

    fun getToken() :String{
        return getPrefs().getString("authToken","").toString()
    }

}