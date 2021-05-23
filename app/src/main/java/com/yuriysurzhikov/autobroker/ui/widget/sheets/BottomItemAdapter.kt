package com.yuriysurzhikov.autobroker.ui.widget.sheets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.ui.list.MutableRecyclerAdapter
import com.yuriysurzhikov.autobroker.ui.list.OnItemClickListener
import com.yuriysurzhikov.autobroker.util.ImageUtils

class BottomItemAdapter<T : IBottomItem> :
    MutableRecyclerAdapter<T, BottomItemAdapter.BottomItemHolder<T>>() {

    private var onItemClickListener: OnItemClickListener<T>? = null
    var listItemRes: Int = R.layout.list_item_bottom_view_item

    class BottomItemHolder<T : IBottomItem>(view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView by lazy { itemView.findViewById<TextView>(android.R.id.text1) }
        private val image: ImageView by lazy { itemView.findViewById<ImageView>(android.R.id.icon) }

        fun bind(
            item: T,
            onItemClickListener: OnItemClickListener<T>?
        ) {
            title.text = item.getTitle()
            ImageUtils.setImageUrl(image, item.getImageUrl())
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(item, adapterPosition)
            }
        }

        companion object {
            @JvmStatic
            fun <T : IBottomItem> instantiate(
                inflater: LayoutInflater,
                parent: ViewGroup,
                viewType: Int,
                @LayoutRes itemRes: Int = R.layout.list_item_bottom_view_item
            ): BottomItemHolder<T> {
                return BottomItemHolder(
                    inflater.inflate(
                        itemRes,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomItemHolder<T> {
        return BottomItemHolder.instantiate(getLayoutInflater(parent), parent, viewType, listItemRes)
    }

    override fun onBindViewHolder(holder: BottomItemHolder<T>, position: Int) {
        this[position]?.let { holder.bind(it, onItemClickListener) }
    }

    fun setOnClickListener(listener: OnItemClickListener<T>?) {
        if (listener != null && listener !== onItemClickListener) {
            onItemClickListener = listener
            notifyDataSetChanged()
        }
    }
}