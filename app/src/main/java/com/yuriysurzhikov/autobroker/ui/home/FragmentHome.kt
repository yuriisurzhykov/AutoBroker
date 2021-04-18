package com.yuriysurzhikov.autobroker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.ui.AbstractFragment

class FragmentHome : AbstractFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        fun newInstance() = FragmentHome()
    }
}