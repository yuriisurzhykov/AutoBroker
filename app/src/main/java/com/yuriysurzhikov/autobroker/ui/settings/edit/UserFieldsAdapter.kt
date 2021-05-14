package com.yuriysurzhikov.autobroker.ui.settings.edit

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.ui.list.MutableRecyclerAdapter
import com.yuriysurzhikov.autobroker.ui.text.SimpleTextWatcher

class UserFieldsAdapter : MutableRecyclerAdapter<IUserField, UserFieldsAdapter.UserFieldHolder>() {

    inner class UserFieldHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val input: TextInputLayout by lazy { itemView.findViewById<TextInputLayout>(android.R.id.input) }
        private val label: TextView by lazy { itemView.findViewById<TextView>(R.id.label) }

        fun bind(item: IUserField) {
            label.text = item.getHint()
            input.hint = item.getHint()
            input.editText?.setText(item.getValue())
            input.editText?.addTextChangedListener(object : SimpleTextWatcher() {
                override fun afterTextChanged(s: Editable?) {
                    mItems[adapterPosition].setValue(s.toString())
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFieldHolder {
        return instantiate(getLayoutInflater(parent), parent, viewType)
    }

    override fun onBindViewHolder(holder: UserFieldHolder, position: Int) {
        holder.bind(this[position]!!)
    }

    private fun instantiate(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): UserFieldHolder {
        return UserFieldHolder(
            inflater.inflate(
                R.layout.list_item_input_field,
                parent,
                false
            )
        )
    }
}