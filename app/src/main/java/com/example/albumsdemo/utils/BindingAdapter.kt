package com.example.albumslistingdemo.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import timber.log.Timber

@BindingAdapter("glideUrl")
fun setImageUrl(
    view: ImageView,
    imageUrl: String?
) {
    Timber.d("Glide load url: $imageUrl")
    Glide.with(view.context)
        .load(imageUrl)
        .error(android.R.color.transparent)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}