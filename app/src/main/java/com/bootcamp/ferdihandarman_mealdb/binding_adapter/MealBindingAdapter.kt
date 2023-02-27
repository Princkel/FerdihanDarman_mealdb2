package com.bootcamp.ferdihandarman_mealdb.binding_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bootcamp.ferdihandarman_mealdb.R
import com.bumptech.glide.Glide

object MealBindingAdapter {
    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {
        val placeHolderDrawable = R.drawable.img_placeholder
        Glide.with(imageView.context).load(imageUrl).placeholder(placeHolderDrawable)
            .error(placeHolderDrawable)
            .into(imageView)
    }
}