package com.yuriysurzhikov.autobroker.ui.widget.fragmentswipe

import androidx.fragment.app.Fragment

interface IFragmentNavigation {
    fun addFragment(
        fragment: Fragment,
        tag: String? = fragment.toString(),
        withBackStack: Boolean = true
    )

    fun popBackStack()
}