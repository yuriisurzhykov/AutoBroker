package com.yuriysurzhikov.autobroker.ui.widget.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.ui.list.OnItemClickListener

class BottomChooser<T : IBottomItem> : BottomSheetDialogFragment() {

    private var onItemClickListener: OnItemClickListener<T>? = null

    private val adapter = BottomItemAdapter()
    private val items = mutableListOf<IBottomItem>()

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
        adapter.setItems(items)
    }

    companion object {
        fun <T : IBottomItem> newInstance(items: List<IBottomItem>) = BottomChooser<T>().apply {
            this.items.clear()
            this.items.addAll(items)
        }
    }
}