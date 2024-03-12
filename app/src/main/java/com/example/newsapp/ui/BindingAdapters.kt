@file:Suppress("DEPRECATION")

package com.example.newsapp.ui

import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.newsapp.R

@BindingAdapter("url")
fun loadImageUrlToImageView(imageView: ImageView, url: String?) {
    Glide.with(imageView).load(url)
        .into(imageView)
}

@BindingAdapter("imageResource")
fun imageResourceIdToButtonDrawable(button: Button, imageId: Int) {
    button.setCompoundDrawablesWithIntrinsicBounds(
        0,
        imageId,
        0,
        0
    )
}

@BindingAdapter("backgroundTint")
fun colorIdToColor(button: Button, colorId: Int) {
    button.backgroundTintList = ContextCompat.getColorStateList(button.context, colorId)
}

@BindingAdapter("backgroundDrawable")
fun backgroundDrawable(button: Button, side: String) {
    if (side == "left") {
        button.setBackgroundDrawable(ContextCompat.getDrawable(button.context, R.drawable.left_bg))
    } else {
        button.setBackgroundDrawable(ContextCompat.getDrawable(button.context, R.drawable.right_bg))
    }
}