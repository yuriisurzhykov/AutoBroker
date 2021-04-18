package com.yuriysurzhikov.autobroker.ui.menu

import androidx.fragment.app.FragmentActivity
import com.yuriysurzhikov.autobroker.ui.finder.FragmentFinder
import com.yuriysurzhikov.autobroker.ui.home.FragmentHome
import com.yuriysurzhikov.autobroker.ui.settings.FragmentUserDetails

class NavigationManager constructor(private val fragmentActivity: FragmentActivity) {
    fun createPagerAdapter(): NavigationPagerAdapter {
        return NavigationPagerAdapter.Builder(fragmentActivity)
            .addItem(FragmentHome.newInstance())
            .addItem(FragmentFinder.newInstance())
            .addItem(FragmentUserDetails.newInstance())
            .build()
    }
}