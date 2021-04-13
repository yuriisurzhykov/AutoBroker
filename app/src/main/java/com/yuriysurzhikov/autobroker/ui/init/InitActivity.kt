package com.yuriysurzhikov.autobroker.ui.init

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.yuriysurzhikov.autobroker.ui.AbstractActivity
import com.yuriysurzhikov.autobroker.ui.login.LoginActivity
import com.yuriysurzhikov.autobroker.ui.main.MainActivity

class InitActivity : AbstractActivity() {

    private val viewModel: InitActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }.also {
                startActivity(it)
            }
    }

    private fun openLoginScreen() {
        Intent(this, LoginActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }.also {
                startActivity(it)
            }
    }
}