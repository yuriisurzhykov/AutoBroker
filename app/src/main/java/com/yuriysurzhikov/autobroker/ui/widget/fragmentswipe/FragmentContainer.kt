package com.yuriysurzhikov.autobroker.ui.widget.fragmentswipe

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.ui.AbstractFragment
import com.yuriysurzhikov.autobroker.util.ViewUtils

class FragmentContainer : AbstractFragment(), IFragmentContainer {

    private lateinit var mToolbar: Toolbar
    private lateinit var mToolbarTitle: TextView
    private var mainFragment: Fragment? = null
    var onBackStackChangeListener: FragmentManager.OnBackStackChangedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (onBackStackChangeListener != null) {
            childFragmentManager.addOnBackStackChangedListener(onBackStackChangeListener!!)
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (onBackStackChangeListener != null) {
            childFragmentManager.removeOnBackStackChangedListener(onBackStackChangeListener!!)
        }
    }

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
        mToolbar.inflateMenu(R.menu.empty_menu)
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
        }
    }

    override fun popBackStack() {
        childFragmentManager.popBackStack()
    }

    override fun refresh() {
        val fragment = childFragmentManager.findFragmentById(R.id.content_container)
        if (fragment is IRefreshableFragment) {
            fragment.refresh()
        }
    }

    override fun goToMain() {
        if (mainFragment != null) {
            childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            childFragmentManager.beginTransaction()
                .replace(R.id.content_container, mainFragment!!, MAIN_FRAGMENT_TAG)
                .addToBackStack(MAIN_FRAGMENT_TAG)
                .commit()
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

    override fun getBackStackCount(): Int {
        return try {
            childFragmentManager.backStackEntryCount
        } catch (e: Throwable) {
            0
        }
    }

    private fun setToolbarByFragment(fragment: Fragment) {
        if (fragment is IStyleFragment) {
            ViewUtils.setVisible(mToolbar)
            mToolbar.setNavigationIcon(fragment.getNavigationIcon() ?: getDefaultNavigationIcon())
            mToolbar.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(fragment.getToolbarColor()))
            mToolbarTitle.text = fragment.getTitle()
        } else if (childFragmentManager.backStackEntryCount == 1) {
            ViewUtils.setGone(mToolbar)
        }
    }

    @DrawableRes
    private fun getDefaultNavigationIcon(): Int {
        return R.drawable.ic_nav_back
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
            val fragment = childFragmentManager.findFragmentById(R.id.content_container)
            fragment?.let {
                setToolbarByFragment(it)
            }
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