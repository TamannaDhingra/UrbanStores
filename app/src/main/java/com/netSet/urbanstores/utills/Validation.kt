package com.netSet.urbanstores.utills

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.netSet.urbanstores.activities.MainActivity
import org.apache.commons.text.StringEscapeUtils
import java.io.IOException
import java.math.BigInteger
import java.util.*
import kotlin.coroutines.coroutineContext

object Validation {

    fun isValidEmail(text: String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }
    fun isValidPhone(text: String):Boolean{
        return Patterns.PHONE.matcher(text).matches()
    }


    //cant
    fun getEmojiFilter(blockChars: String, context: Context): Array<InputFilter> {
        return arrayOf(label@ InputFilter { source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
            val source1: String = StringEscapeUtils.escapeJava(source.toString())
            var i = start
            while (i < end) {
                if (source != null && blockChars.contains("" + source1[i])) {
                  //  (context as MainActivity).showSnackBar("Special character & emoji not allowed")
                    return@InputFilter source.subSequence(start, i)
                }
                i++
            }
            null
        })
    }

//keyboard hide when max length is completed
    fun hideKeyboardFormUser(context: Context) {
        val view: View? = (context as MainActivity).currentFocus
        val hideKeyboard: InputMethodManager? = (context as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        hideKeyboard?.hideSoftInputFromWindow(view?.windowToken, 0)
    }
    private fun drawableToBitmap(drawable: Drawable): Bitmap? {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
    fun getAddress(lat: Double, lng: Double,context: Context) :String{
        var name:String=""
        var obj: Address? =null
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
            if (addresses.size>0) {
                obj = addresses[0]

                var add: String = obj!!.getAddressLine(0)
                Log.v("IGA", "Address$add")
                Log.v("IGA New", "Address ${"subAdminArea: "}${obj.subAdminArea}" +
                        ",${"thoroughfare: "}${obj.thoroughfare}" +
                        ",${"subThoroughfare: "}${obj.subThoroughfare}" +
                        ",${"adminArea: "}${obj.adminArea}" +
                        ",${"featureName: "}${obj.featureName}" +
                        ",${"locality: "}${obj.locality}" +
                        ",${"subLocality: "}${obj.subLocality}," +
                        "${"premises: "}${obj.premises}")

                name=add


            }else{

                name="${"UnKnown Location..."}"
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return name
    }


}