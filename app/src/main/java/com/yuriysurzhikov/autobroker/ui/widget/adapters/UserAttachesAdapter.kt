package com.yuriysurzhikov.autobroker.ui.widget.adapters

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.ui.list.MutableRecyclerAdapter
import com.yuriysurzhikov.autobroker.ui.list.OnItemClickListener
import com.yuriysurzhikov.autobroker.util.ImageUtils

class UserAttachesAdapter :
    MutableRecyclerAdapter<UserAttachesAdapter.UserAttach, RecyclerView.ViewHolder>() {

    var onAddClickListener: View.OnClickListener? = null
    var onItemClickListener: OnItemClickListener<UserAttach>? = null
    var onDeleteClickListener: OnItemClickListener<UserAttach>? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView by lazy { itemView.findViewById<ImageView>(android.R.id.icon) }
        private val removeButton: ImageView by lazy { itemView.findViewById<ImageView>(android.R.id.closeButton) }

        fun bind(userAttach: UserAttach) {
            ImageUtils.setImageUri(imageView, userAttach.uri)
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(userAttach, adapterPosition)
            }
            removeButton.setOnClickListener {
                onDeleteClickListener?.onItemClick(userAttach, adapterPosition)
            }
        }
    }

    inner class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView by lazy { itemView.findViewById<ImageView>(android.R.id.icon) }
        fun bind() {
            imageView.setImageResource(R.drawable.ic_picture)
            itemView.setOnClickListener(onAddClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_REGULAR -> ViewHolder(
                getLayoutInflater(parent).inflate(
                    R.layout.layout_image_view,
                    parent,
                    false
                )
            )
            else -> EmptyViewHolder(
                getLayoutInflater(parent).inflate(
                    R.layout.layout_image_view_full_width,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> holder.bind(this[position]!!)
            is EmptyViewHolder -> holder.bind()
        }
    }

    override fun getItemCount() = if (mItems.isEmpty()) 1 else super.getItemCount()

    override fun getItemViewType(position: Int): Int {
        return if (mItems.isEmpty() && position > RecyclerView.NO_POSITION) VIEW_TYPE_EMPTY else VIEW_TYPE_REGULAR
    }

    companion object {
        private const val VIEW_TYPE_REGULAR = 1
        private const val VIEW_TYPE_EMPTY = 2
    }


    open class UserAttach(var uri: Uri?) : Parcelable {
        constructor(parcel: Parcel) : this(parcel.readParcelable<Uri>(Uri::class.java.classLoader)) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeParcelable(uri, flags)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<UserAttach> {
            override fun createFromParcel(parcel: Parcel): UserAttach {
                return UserAttach(parcel)
            }

            override fun newArray(size: Int): Array<UserAttach?> {
                return arrayOfNulls(size)
            }
        }
    }
}