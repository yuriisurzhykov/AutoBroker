package com.yuriysurzhikov.autobroker.ui.widget.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.model.entity.Region

class RegionAdapter(context: Context, objects: Array<out Region>) :
    ArrayAdapter<Region>(context, R.layout.spinner_item_region, objects) {

    private val mItems = objects.toMutableList()

    init {
        setDropDownViewResource(R.layout.spinner_item_region)
    }

    override fun getCount() = mItems.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = super.getView(position, convertView, parent) as TextView
        item.text = mItems[position].localizations[0].value
        return item
    }
}