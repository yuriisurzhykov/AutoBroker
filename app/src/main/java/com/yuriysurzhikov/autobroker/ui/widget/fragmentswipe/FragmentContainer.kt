package com.yuriysurzhikov.autobroker.ui.widget.fragmentswipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.ui.AbstractFragment

class FragmentContainer : AbstractFragment(), IFragmentContainer {

    private lateinit var mFrameLayout: FrameLayout
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
        mFrameLayout = view.findViewById(R.id.content_container)
        if (mainFragment != null) {
            goToMain()
        }
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

    override fun goToMain() {
        if (mainFragment != null) {
            childFragmentManager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            childFragmentManager.beginTransaction()
                .replace(R.id.content_container, mainFragment!!, "main_fragment")
                .addToBackStack("main_fragment")
                .commit()
        }
    }

    override fun setMainFragment(fragment: Fragment) {
        mainFragment = fragment
    }

    companion object {
        @JvmStatic
        fun newInstance(mainFragment: Fragment) = FragmentContainer().apply {
            this.mainFragment = mainFragment
        }
    }
}