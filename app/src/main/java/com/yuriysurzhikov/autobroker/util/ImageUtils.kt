package com.yuriysurzhikov.autobroker.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

object ImageUtils {
    fun setImageRes(view: ImageView, @DrawableRes res: Int) {
        Glide.with(view)
            .load(res)
            .into(view)
    }
}