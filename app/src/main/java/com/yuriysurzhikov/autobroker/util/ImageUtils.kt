package com.yuriysurzhikov.autobroker.util

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

object ImageUtils {

    /**
     * @param view ImageView to which set res.
     * @param res Drawable resource value.
     */
    fun setImageRes(view: ImageView, @DrawableRes res: Int) {
        Glide.with(view)
            .load(res)
            .into(view)
    }

    /**
     * @param view ImageView to which set res.
     * @param url String url which contains the picture.
     */
    fun setImageUrl(view: ImageView, url: String?) {
        Glide.with(view)
            .load(url)
            .circleCrop()
            .into(view)
    }

    /**
     * @param view ImageView to which set res.
     * @param uri Uri which contains the picture.
     */
    fun setImageUri(view: ImageView, uri: Uri?) {
        Glide.with(view)
            .load(uri)
            .circleCrop()
            .into(view)
    }
}