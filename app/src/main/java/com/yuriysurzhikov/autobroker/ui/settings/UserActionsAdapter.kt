package com.yuriysurzhikov.autobroker.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.model.entity.Action
import com.yuriysurzhikov.autobroker.ui.list.BaseViewHolder
import com.yuriysurzhikov.autobroker.ui.list.MutableRecyclerAdapter
import com.yuriysurzhikov.autobroker.ui.list.OnItemClickListener
import com.yuriysurzhikov.autobroker.ui.widget.groupedrecycler.GroupContainer
import com.yuriysurzhikov.autobroker.ui.widget.groupedrecycler.GroupedRecyclerAdapter
import com.yuriysurzhikov.autobroker.util.ImageUtils
import com.yuriysurzhikov.autobroker.util.ViewUtils
import com.yuriysurzhikov.autobroker.util.isNotNullOrEmpty

class UserActionsAdapter :
    MutableRecyclerAdapter<Action, UserActionsAdapter.ActionViewHolder>() {

    inner class GroupView(view: View) : RecyclerView.ViewHolder(view)

    class ActionViewHolder(view: View) : RecyclerView.ViewHolder(view),
        BaseViewHolder<Action> {

        private var onItemClickListener: OnItemClickListener<Action>? = null

        private val actionTitle: TextView by lazy { itemView.findViewById<TextView>(R.id.action_title) }
        private val actionIcon: ImageView by lazy { itemView.findViewById<ImageView>(R.id.action_icon) }
        private val actionBadgeText: TextView by lazy { itemView.findViewById<TextView>(R.id.badge_text) }

        private var mItem: Action? = null

        override fun bind(item: Action, imageBaseUrl: String?) {
            mItem = item
            actionTitle.text = item.label
            if (item.badge.isNotNullOrEmpty()) {
                ViewUtils.setVisible(actionBadgeText)
                actionBadgeText.text = item.badge
            } else {
                ViewUtils.setGone(actionBadgeText)
            }
            if (item.iconRes != 0) {
                ImageUtils.setImageRes(actionIcon, item.iconRes)
                ViewUtils.setVisible(actionIcon)
            } else {
                ViewUtils.setGone(actionIcon)
            }
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(item, adapterPosition)
            }
        }

        override fun setOnItemClickListener(listener: OnItemClickListener<Action>?) {
            onItemClickListener = listener
        }

        override val item: Action?
            get() = mItem

        companion object {
            fun instantiate(
                inflater: LayoutInflater,
                parent: ViewGroup,
                viewType: Int
            ): ActionViewHolder {
                return ActionViewHolder(
                    inflater.inflate(
                        R.layout.list_item_action,
                        parent,
                        false
                    )
                )
            }
        }
    }

    private var onItemClickListener: OnItemClickListener<Action>? = null

    fun setOnActionClickListener(listener: OnItemClickListener<Action>?) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        return ActionViewHolder.instantiate(
            getLayoutInflater(parent),
            parent,
            viewType
        )
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.setOnItemClickListener(onItemClickListener)
        holder.bind(mItems[position], null)
    }
}