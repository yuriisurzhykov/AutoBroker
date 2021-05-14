package com.yuriysurzhikov.autobroker.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.ui.list.MutableRecyclerAdapter
import com.yuriysurzhikov.autobroker.util.ImageUtils

class CarBrandAdapter : MutableRecyclerAdapter<CarBrand, CarBrandAdapter.CarBrandHolder>() {

    class CarBrandHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name: TextView by lazy { itemView.findViewById<TextView>(R.id.model_name) }
        private val icon: ImageView by lazy { itemView.findViewById<ImageView>(R.id.model_icon) }

        fun bind(item: CarBrand) {
            name.text = item.name
            ImageUtils.setImageUrl(icon, item.iconUrl)
        }

        companion object {
            @JvmStatic
            fun instantiate(
                inflater: LayoutInflater,
                parent: ViewGroup,
                viewType: Int
            ): CarBrandHolder {
                return CarBrandHolder(inflater.inflate(R.layout.list_item_car_model, parent, false))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarBrandHolder {
        return CarBrandHolder.instantiate(getLayoutInflater(parent), parent, viewType)
    }

    override fun onBindViewHolder(holder: CarBrandHolder, position: Int) {
        this[position]?.let { holder.bind(it) }
    }
}