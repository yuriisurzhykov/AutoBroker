package com.yuriysurzhikov.autobroker.ui.image

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.ui.list.MutableRecyclerAdapter
import com.yuriysurzhikov.autobroker.util.ImageUtils

class ImageListAdapter : MutableRecyclerAdapter<Uri, ImageListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView by lazy { itemView.findViewById<ImageView>(android.R.id.icon) }

        fun bind(item: Uri) {
            ImageUtils.setImageUri(imageView, item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            getLayoutInflater(parent).inflate(
                R.layout.layout_image_full_screen,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this[position]?.let {
            holder.bind(it)
        }
    }
}