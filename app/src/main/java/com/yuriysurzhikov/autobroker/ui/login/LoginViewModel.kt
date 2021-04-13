package com.yuriysurzhikov.autobroker.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {

    private val userExists = MutableLiveData<Boolean>()

    fun tryLogin(user: FirebaseUser?) {

    }
}