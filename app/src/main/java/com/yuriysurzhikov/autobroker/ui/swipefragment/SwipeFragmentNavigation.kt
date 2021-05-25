package com.yuriysurzhikov.autobroker.ui.swipefragment

import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yuriysurzhikov.autobroker.ui.widget.fragmentswipe.FragmentContainer

class SwipeFragmentNavigation {

    private lateinit var mBottomNavView: BottomNavigationView
    private lateinit var mViewPager2: ViewPager2
    private lateinit var mPagerAdapter: FragmentContainerAdapter
    private val idsIndexed = mutableMapOf<Int, Int>() // First is INDEX second is menu ID
    private val mFragmentContainers =
        mutableMapOf<Int, FragmentContainer>() // First is menu ID second is FragmentContainer
    private var mCurrentFragmentContainer: FragmentContainer? = null

    fun setBottomMenuSelectListener(listener: BottomNavigationView.OnNavigationItemSelectedListener?) {
        mBottomNavView.setOnNavigationItemSelectedListener { item ->
            if (mCurrentFragmentContainer != null) {
                mCurrentFragmentContainer!!.goToMain()
            }
            mCurrentFragmentContainer = mFragmentContainers[item.itemId]
            listener?.onNavigationItemSelected(item)
            return@setOnNavigationItemSelectedListener true
        }
    }

    fun setOnPageChangeCallback(callback: ViewPager2.OnPageChangeCallback) {
        mViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val index = idsIndexed[position]
                if (index != null) {
                    mCurrentFragmentContainer = mFragmentContainers[index]
                }
                callback.onPageSelected(position)
            }
        })
    }

    fun showFragment(fragment: Fragment, tag: String? = fragment.toString()) {
        mCurrentFragmentContainer?.addFragment(fragment, tag)
        //onBackStackChanged()
    }

    fun onBackPressed(): Boolean {
        return if (mCurrentFragmentContainer != null) {
            val changed = mCurrentFragmentContainer!!.onBackPressed()
            //onBackStackChanged()
            changed
        } else {
            false
        }
    }

    fun refresh() {
        mCurrentFragmentContainer?.refresh()
    }

    fun backToMain() {
        mCurrentFragmentContainer?.goToMain()
    }

    private fun onBackStackChanged() {
        if (mCurrentFragmentContainer != null)
            mViewPager2.isUserInputEnabled = mCurrentFragmentContainer!!.getBackStackCount() <= 1
        else
            mViewPager2.isUserInputEnabled = true
    }

    class Builder {

        private val mSwipeNavigation = SwipeFragmentNavigation()

        constructor(fragmentActivity: FragmentActivity) {
            mSwipeNavigation.mPagerAdapter = FragmentContainerAdapter(fragmentActivity)
        }

        constructor(fragment: Fragment) {
            mSwipeNavigation.mPagerAdapter = FragmentContainerAdapter(fragment)
        }

        constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) {
            mSwipeNavigation.mPagerAdapter = FragmentContainerAdapter(fragmentManager, lifecycle)
        }

        fun withBottomNavigationView(view: BottomNavigationView): Builder {
            mSwipeNavigation.mBottomNavView = view
            return this
        }

        fun withViewPager(pager: ViewPager2): Builder {
            mSwipeNavigation.mViewPager2 = pager
            mSwipeNavigation.mViewPager2.adapter = mSwipeNavigation.mPagerAdapter
            return this
        }

        fun withFragments(items: List<Fragment>): Builder {
            mSwipeNavigation.mPagerAdapter.setItems(items)
            mSwipeNavigation.idsIndexed.clear()
            mSwipeNavigation.mBottomNavView.menu.forEachIndexed { index, item ->
                mSwipeNavigation.idsIndexed[index] = item.itemId
                val fragmentContainer = mSwipeNavigation.mPagerAdapter.get(index)
                fragmentContainer.onBackStackChangeListener =
                    FragmentManager.OnBackStackChangedListener {
                        mSwipeNavigation.onBackStackChanged()
                    }
                mSwipeNavigation.mFragmentContainers[item.itemId] = fragmentContainer

            }
            return this
        }

        fun setOnNavigationListener(listener: BottomNavigationView.OnNavigationItemSelectedListener?): Builder {
            mSwipeNavigation.setBottomMenuSelectListener(listener)
            return this
        }

        fun setOnPageChangeCallback(listener: ViewPager2.OnPageChangeCallback): Builder {
            mSwipeNavigation.setOnPageChangeCallback(listener)
            return this
        }

        fun build(): SwipeFragmentNavigation {
            if (mSwipeNavigation::mBottomNavView.isInitialized && mSwipeNavigation::mViewPager2.isInitialized) {
                return mSwipeNavigation
            } else throw RuntimeException("You must to provide BottomNavigationView and ViewPager2 for build SwipeFragmentNavigation")
        }
    }
}