package com.yuriysurzhikov.autobroker.ui.widget.fragmentswipe

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.ui.AbstractFragment
import com.yuriysurzhikov.autobroker.util.ViewUtils

class FragmentContainer : AbstractFragment(), IFragmentContainer {

    private lateinit var mToolbar: Toolbar
    private lateinit var mToolbarTitle: TextView
    private var mainFragment: Fragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mToolbar = view.findViewById(R.id.fragment_toolbar)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        mToolbarTitle = view.findViewById(R.id.toolbar_title)
        childFragmentManager.addOnBackStackChangedListener(backStackListener)
        goToMain()
    }

    override fun addFragment(fragment: Fragment, tag: String?, withBackStack: Boolean) {
        if (!isDetached) {
            val ft = childFragmentManager.beginTransaction()
            ft.replace(R.id.content_container, fragment, tag)
            if (withBackStack) {
                ft.addToBackStack(tag)
            }
            ft.commit()
            setToolbarByFragment(fragment)
        }
    }

    override fun popBackStack() {
        childFragmentManager.popBackStack()
    }

    override fun goToMain() {
        if (mainFragment != null) {
            childFragmentManager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            childFragmentManager.beginTransaction()
                .replace(R.id.content_container, mainFragment!!, MAIN_FRAGMENT_TAG)
                .addToBackStack(MAIN_FRAGMENT_TAG)
                .commit()
            setToolbarByFragment(mainFragment!!)
        }
    }

    override fun setMainFragment(fragment: Fragment) {
        mainFragment = fragment
    }

    override fun onBackPressed(): Boolean {
        if (childFragmentManager.backStackEntryCount > 1) {
            childFragmentManager.popBackStack()
            return true
        }
        return false
    }

    private fun setToolbarByFragment(fragment: Fragment) {
        if (fragment is IStyleFragment) {
            ViewUtils.setVisible(mToolbar)
            mToolbar.setNavigationIcon(fragment.getNavigationIcon())
            mToolbar.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(fragment.getToolbarColor()))
            mToolbarTitle.text = fragment.getTitle()
        } else if (childFragmentManager.backStackEntryCount == 1) {
            ViewUtils.setGone(mToolbar)
        }
    }

    private val backStackListener = FragmentManager.OnBackStackChangedListener {
        if (childFragmentManager.backStackEntryCount > 1) {
            ViewUtils.setVisible(mToolbar)
            mToolbar.navigationIcon =
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_nav_back,
                    context?.theme
                )
        } else {
            setToolbarByFragment(mainFragment!!)
        }
    }

    companion object {

        private const val MAIN_FRAGMENT_TAG = "main_fragment"

        @JvmStatic
        fun newInstance(mainFragment: Fragment) = FragmentContainer().apply {
            this.mainFragment = mainFragment
        }
    }
}