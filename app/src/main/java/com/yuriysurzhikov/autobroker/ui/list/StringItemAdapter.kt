package com.yuriysurzhikov.autobroker.ui.list

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.model.entity.StringItem

class StringItemAdapter :
    MutableRecyclerAdapter<StringItem, StringItemAdapter.StringItemViewHolder>() {

    inner class StringItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val label: TextView by lazy { itemView.findViewById<TextView>(android.R.id.text1) }
        private val value: TextView by lazy { itemView.findViewById<TextView>(android.R.id.text2) }

        fun bind(item: StringItem) {
            label.text = item.label
            value.text = item.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringItemViewHolder {
        return StringItemViewHolder(
            getLayoutInflater(parent).inflate(
                R.layout.list_item_field,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StringItemViewHolder, position: Int) {
        holder.bind(mItems[position])
    }
}