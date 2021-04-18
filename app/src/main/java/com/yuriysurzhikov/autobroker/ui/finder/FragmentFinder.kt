package com.yuriysurzhikov.autobroker.ui.finder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.ui.AbstractFragment

class FragmentFinder : AbstractFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_finder, container, false)
    }

    companion object {
        fun newInstance() = FragmentFinder()
    }
}