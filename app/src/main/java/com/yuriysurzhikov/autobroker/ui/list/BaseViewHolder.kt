package com.yuriysurzhikov.autobroker.ui.list

interface BaseViewHolder<T> {
    fun bind(item: T, imageBaseUrl: String?)
    fun setOnItemClickListener(listener: OnItemClickListener<T>?)
    val item: T?
}