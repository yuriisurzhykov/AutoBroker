package com.yuriysurzhikov.autobroker.ui.widget.fragmentswipe

import androidx.fragment.app.Fragment

interface IFragmentContainer : IFragmentNavigation {

    fun goToMain()

    fun setMainFragment(fragment: Fragment)

    fun onBackPressed(): Boolean
}