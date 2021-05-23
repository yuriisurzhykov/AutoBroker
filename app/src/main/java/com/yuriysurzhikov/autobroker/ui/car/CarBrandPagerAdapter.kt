package com.yuriysurzhikov.autobroker.ui.car

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.ui.list.MutableRecyclerAdapter
import com.yuriysurzhikov.autobroker.util.ImageUtils

class CarBrandPagerAdapter : MutableRecyclerAdapter<CarBrand, CarBrandPagerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val brandImage: ImageView by lazy { itemView.findViewById<ImageView>(R.id.brand_image) }
        private val brandName: TextView by lazy { itemView.findViewById<TextView>(R.id.brand_name) }

        fun bind(item: CarBrand) {
            ImageUtils.setImageUrl(brandImage, item.iconUrl)
            brandName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(getLayoutInflater(parent).inflate(R.layout.item_car_brand, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this[position]?.let {
            holder.bind(it)
        }
    }
}