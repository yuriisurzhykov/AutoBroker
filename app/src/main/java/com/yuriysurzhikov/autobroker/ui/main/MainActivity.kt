package com.yuriysurzhikov.autobroker.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.databinding.ActivityMainBinding
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.ui.AbstractActivity
import com.yuriysurzhikov.autobroker.ui.login.LoginActivity

class MainActivity : AbstractActivity() {

    private val TAG = MainActivity::class.simpleName
    override fun getLayoutRes() = R.layout.activity_main

    override fun onCreated(savedInstanceState: Bundle?) {
        findViewById<BottomNavigationView>(R.id.bottom_menu).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.first -> {
                    signOut()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
        viewModel.observeUser(this, userObserver)
    }

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.also {
            startActivity(it)
        }
    }

    private val userObserver = Observer<User?> {

    }
}