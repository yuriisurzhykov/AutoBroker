package com.yuriysurzhikov.autobroker.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.databinding.ListItemCarBinding
import com.yuriysurzhikov.autobroker.model.entity.Car
import com.yuriysurzhikov.autobroker.ui.image.ImageListAdapter
import com.yuriysurzhikov.autobroker.ui.list.MutableRecyclerAdapter
import com.yuriysurzhikov.autobroker.util.ImageUtils

class CarsAdapter : MutableRecyclerAdapter<Car, CarsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val adapter = ImageListAdapter()
        fun bind(item: Car) {
            val binding = DataBindingUtil.bind<ListItemCarBinding>(itemView)
            binding?.let { view ->
                view.model = item
                if (item.images.isNotEmpty()) {
                    ImageUtils.setImageUrl(view.icon, item.images[0])
                }
            }
        }

        companion object {
            @JvmStatic
            fun instantiate(inflater: LayoutInflater, parent: ViewGroup) =
                ViewHolder(inflater.inflate(R.layout.list_item_car, parent, false))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.instantiate(getLayoutInflater(parent), parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this[position]?.let {
            holder.bind(it)
        }
    }
}