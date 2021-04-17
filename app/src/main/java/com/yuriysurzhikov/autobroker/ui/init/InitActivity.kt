package com.yuriysurzhikov.autobroker.ui.init

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.model.events.SyncFailedEvent
import com.yuriysurzhikov.autobroker.model.events.SyncSuccessEvent
import com.yuriysurzhikov.autobroker.repository.ErrorCode
import com.yuriysurzhikov.autobroker.ui.AbstractActivity
import com.yuriysurzhikov.autobroker.ui.login.LoginActivity
import com.yuriysurzhikov.autobroker.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitActivity : AbstractActivity() {

    private val TAG = InitActivity::class.simpleName

    private val viewModel: InitActivityViewModel by viewModels()
    override fun getLayoutRes() = R.layout.activity_splash_layout
    private var userLoginStatus: Int? = null
    private var syncComplete = false

    override fun onCreated(savedInstanceState: Bundle?) {
        viewModel.setLoggingObserver(this, userLoggingObserver)
    }

    private val userLoggingObserver = Observer<Int> {
        it?.let {
            performLogin(it)
        }
    }

    override fun onSyncSuccess(event: SyncSuccessEvent) {
        super.onSyncSuccess(event)
        viewModel.checkUserSignIn()
    }

    override fun onSyncFailed(event: SyncFailedEvent) {
        super.onSyncFailed(event)
        viewModel.checkUserSignIn()
    }

    private fun performLogin(loginStatus: Int) {
        when (loginStatus) {
            ErrorCode.OK -> openMainScreen()
            ErrorCode.ERROR_ON_BOARDING_NEEDED -> openLoginScreen(Bundle().apply {
                putBoolean(LoginActivity.ARG_OPEN_ON_BOARDING, true)
            })
            ErrorCode.ERROR_NO_SUCH_USER -> openLoginScreen()
            else -> openLoginScreen()
        }
    }

    private fun openMainScreen() {
        Intent(this, MainActivity::class.java)
            .apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }.also {
                startActivity(it)
            }
    }

    private fun openLoginScreen(data: Bundle? = null) {
        Intent(this, LoginActivity::class.java)
            .apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                putExtra(LoginActivity.ARG_BUNDLE, data)
            }.also {
                startActivity(it)
            }
    }
}