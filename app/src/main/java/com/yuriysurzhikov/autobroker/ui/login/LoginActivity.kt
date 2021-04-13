package com.yuriysurzhikov.autobroker.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.yuriysurzhikov.autobroker.AutoBrokerApp
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.repository.sync.SyncAdapter
import com.yuriysurzhikov.autobroker.ui.AbstractActivity
import com.yuriysurzhikov.autobroker.ui.main.MainActivity
import com.yuriysurzhikov.autobroker.util.Const

class LoginActivity :
    AbstractActivity(),
    ILoginCallback {

    private val TAG = LoginActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        openFragment(MainLoginFragment(), "main_login_fragment")
    }

    fun openFragment(fragment: Fragment, tag: String?) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    override fun openOnBoarding() {
        val fragment = UserDataFragment.newInstance()
        openFragment(fragment, "on-boarding_fragment")
    }

    override fun onLoginSuccess(user: User) {
        AutoBrokerApp.sync(this, Bundle().apply {
            putString(SyncAdapter.ARG_USER_ID, user.strId)
        })
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }.also {
            startActivity(it)
        }
    }
}