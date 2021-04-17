package com.yuriysurzhikov.autobroker.ui.init

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.repository.core.ISynchronizer
import com.yuriysurzhikov.autobroker.ui.AbstractActivity
import com.yuriysurzhikov.autobroker.ui.login.LoginActivity
import com.yuriysurzhikov.autobroker.ui.main.MainActivity
import javax.inject.Inject
import kotlin.math.log

class InitActivity : AbstractActivity() {

    private val TAG = InitActivity::class.simpleName

    private val viewModel: InitActivityViewModel by viewModels()
    override fun getLayoutRes() = R.layout.activity_splash_layout

    override fun onCreated(savedInstanceState: Bundle?) {
        viewModel.setLoggingObserver(this, userLoggingObserver)
    }

    private val userLoggingObserver = Observer<Boolean> {
        if (it == true) {
            openMainScreen()
        } else {
            openLoginScreen()
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

    private fun openLoginScreen() {
        Intent(this, LoginActivity::class.java)
            .apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }.also {
                startActivity(it)
            }
    }
}