package com.yuriysurzhikov.autobroker.ui.widget.fragmentswipe

import androidx.fragment.app.Fragment

interface IFragmentContainer : IFragmentNavigation, IRefreshableFragment {

    fun goToMain()

    fun setMainFragment(fragment: Fragment)

    fun onBackPressed(): Boolean

    fun getBackStackCount(): Int
}