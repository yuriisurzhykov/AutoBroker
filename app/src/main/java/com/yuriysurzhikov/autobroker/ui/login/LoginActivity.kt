package com.yuriysurzhikov.autobroker.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yuriysurzhikov.autobroker.AutoBrokerApplication
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.repository.core.ISynchronizer
import com.yuriysurzhikov.autobroker.ui.AbstractActivity
import com.yuriysurzhikov.autobroker.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity :
    AbstractActivity(),
    ILoginCallback {

    private val TAG = LoginActivity::class.simpleName

    @Inject
    lateinit var synchronizer: ISynchronizer

    override fun getLayoutRes() = R.layout.activity_login

    override fun onCreated(savedInstanceState: Bundle?) {
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

    override fun onLoginSuccess() {
        AutoBrokerApplication.sync(synchronizer)
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }.also {
            startActivity(it)
        }
    }

    companion object {
        const val ARG_BUNDLE = "extra_parameters"
        const val ARG_OPEN_ON_BOARDING = "arg_needed_onboarding"
    }
}