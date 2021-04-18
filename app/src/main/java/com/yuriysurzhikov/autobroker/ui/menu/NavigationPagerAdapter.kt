package com.yuriysurzhikov.autobroker.ui.menu

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class NavigationPagerAdapter constructor(fragmentManager: FragmentActivity) :
    FragmentStateAdapter(fragmentManager) {

    private val mItems = mutableListOf<Fragment>()
    private val mTitles = mutableListOf<String?>()

    override fun getItemCount() = mItems.size

    override fun createFragment(position: Int) = mItems[position]

    class Builder(private val parent: FragmentActivity) {

        private lateinit var navigationPagerAdapter: NavigationPagerAdapter

        fun addItem(fragment: Fragment, title: String? = null): Builder {
            if (!this::navigationPagerAdapter.isInitialized) {
                navigationPagerAdapter = NavigationPagerAdapter(parent)
            }
            navigationPagerAdapter.mItems.add(fragment)
            navigationPagerAdapter.mTitles.add(title)
            return this
        }

        fun build(): NavigationPagerAdapter {
            return navigationPagerAdapter
        }
    }
}