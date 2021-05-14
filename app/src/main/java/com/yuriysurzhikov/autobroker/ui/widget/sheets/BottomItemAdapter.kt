package com.yuriysurzhikov.autobroker.ui.widget.sheets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.ui.list.MutableRecyclerAdapter
import com.yuriysurzhikov.autobroker.util.ImageUtils

class BottomItemAdapter :
    MutableRecyclerAdapter<IBottomItem, BottomItemAdapter.BottomItemHolder>() {

    class BottomItemHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView by lazy { itemView.findViewById<TextView>(android.R.id.text1) }
        private val image: ImageView by lazy { itemView.findViewById<ImageView>(android.R.id.icon) }

        fun bind(item: IBottomItem) {
            title.text = item.getTitle()
            ImageUtils.setImageUrl(image, item.getImageUrl())
        }

        companion object {
            @JvmStatic
            fun instantiate(
                inflater: LayoutInflater,
                parent: ViewGroup,
                viewType: Int
            ): BottomItemHolder {
                return BottomItemHolder(
                    inflater.inflate(
                        R.layout.list_item_bottom_view_item,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomItemHolder {
        return BottomItemHolder.instantiate(getLayoutInflater(parent), parent, viewType)
    }

    override fun onBindViewHolder(holder: BottomItemHolder, position: Int) {
        this[position]?.let { holder.bind(it) }
    }
}