package com.yuriysurzhikov.autobroker.util

import android.net.Uri
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("photoUri")
fun setImageSrcByUri(view: ImageView, uri: Uri?) {
    Glide.with(view)
        .load(uri)
        .into(view)
}

@BindingAdapter("adapter")
fun setSpinnerAdapter(view: Spinner, adapter: ArrayAdapter<*>?) {
    view.adapter = adapter
}

@BindingAdapter("textWatcher")
fun setTextWatcher(view: EditText, watcher: TextWatcher?) {
    view.addTextChangedListener(watcher)
}