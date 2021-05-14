package com.yuriysurzhikov.autobroker.ui.navigtor

import android.content.Context
import androidx.fragment.app.Fragment
import com.yuriysurzhikov.autobroker.model.entity.Action
import com.yuriysurzhikov.autobroker.ui.INavigationCallbacks
import com.yuriysurzhikov.autobroker.ui.settings.edit.FragmentUserDetails
import com.yuriysurzhikov.autobroker.util.Const

class ActionNavigator : IBaseNavigator<Action> {

    private var navigationCallback: INavigationCallbacks? = null
    private var targetFragment: Fragment? = null

    override fun attach(context: Context, fragment: Fragment) {
        check(context is INavigationCallbacks) {
            throw IllegalArgumentException("Provided context must implement INavigationCallbacks")
        }
        navigationCallback = context
        targetFragment = fragment
    }

    override fun detach() {
        navigationCallback = null
    }

    override fun onItemClick(item: Action, position: Int) {
        when (item.reference) {
            Const.GeneralConst.ACTION_LOGOUT -> {
                navigationCallback?.attemptLogout()
            }
            Const.GeneralConst.ACTION_FULL_INFO -> {
                navigationCallback?.showFragment(FragmentUserDetails.newInstance())
            }
        }
    }
}