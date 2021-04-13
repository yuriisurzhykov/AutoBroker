package com.yuriysurzhikov.autobroker.util

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("photoUri")
fun setImageSrcByUri(view: ImageView, uri: Uri?) {
    Glide.with(view)
        .load(uri)
        .into(view)
}