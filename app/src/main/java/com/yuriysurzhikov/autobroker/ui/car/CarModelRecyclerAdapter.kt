package com.yuriysurzhikov.autobroker.ui.car

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.model.entity.CarModel
import com.yuriysurzhikov.autobroker.ui.list.MutableRecyclerAdapter
import com.yuriysurzhikov.autobroker.util.ImageUtils

class CarModelRecyclerAdapter :
    MutableRecyclerAdapter<CarModel, CarModelRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val modelImage: ImageView by lazy { itemView.findViewById<ImageView>(android.R.id.icon) }
        private val modelName: TextView by lazy { itemView.findViewById<TextView>(android.R.id.text1) }

        fun bind(item: CarModel) {
            ImageUtils.setImageUrl(modelImage, item.imageSrc)
            modelName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            getLayoutInflater(parent).inflate(
                R.layout.list_item_car_model,
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