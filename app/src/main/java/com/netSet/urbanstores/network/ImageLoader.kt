package com.netSet.urbanstores.network

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageLoader {

    @JvmStatic
   @BindingAdapter("ProfileImage")
    fun setImage(view : ImageView, imageUrl : String) {
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("UserImage")
    fun UserImage(view : ImageView, imageUrl : String) {
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }


}