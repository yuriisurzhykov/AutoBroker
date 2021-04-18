package com.yuriysurzhikov.autobroker.ui

import android.content.Intent
import androidx.fragment.app.Fragment

interface INavigationCallbacks {
    fun showFragment(fragment: Fragment, tag: String? = fragment.toString())
    fun openIntent(intent: Intent)
    fun attemptLogout()
}