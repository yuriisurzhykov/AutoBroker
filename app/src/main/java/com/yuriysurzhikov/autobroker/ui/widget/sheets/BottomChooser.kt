package com.yuriysurzhikov.autobroker.ui.widget.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.ui.list.OnItemClickListener

class BottomChooser<T : IBottomItem> : BottomSheetDialogFragment() {

    private var onItemClickListener: OnItemClickListener<T>? = null

    @LayoutRes
    private var listItemRes = R.layout.list_item_bottom_view_item

    private val adapter = BottomItemAdapter<T>()
    private val items = mutableListOf<T>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_chooser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
        adapter.listItemRes = listItemRes
        adapter.setOnClickListener(onItemClickListener)
        adapter.setItems(items)
    }

    companion object {

        @JvmStatic
        @JvmOverloads
        fun <T : IBottomItem> newInstance(
            items: List<T>,
            clickListener: OnItemClickListener<T>? = null,
            @LayoutRes itemRes: Int = R.layout.list_item_bottom_view_item
        ) = BottomChooser<T>().apply {
            this.items.clear()
            this.items.addAll(items)
            this.onItemClickListener = object : OnItemClickListener<T> {
                override fun onItemClick(item: T, position: Int) {
                    clickListener?.onItemClick(item, position)
                    this@apply.dismiss()
                }
            }
            this.listItemRes = itemRes
        }
    }
}