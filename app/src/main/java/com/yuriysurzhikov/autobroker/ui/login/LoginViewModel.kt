package com.yuriysurzhikov.autobroker.ui.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.yuriysurzhikov.autobroker.repository.IUserRepository
import javax.inject.Inject

class LoginViewModel
@Inject
constructor(
    localRepository: IUserRepository
) : ViewModel() {

    private val userExists = MutableLiveData<Boolean>()
    private val loginCode = MutableLiveData<Int>()

    fun observeResult(owner: LifecycleOwner, observer: Observer<Int>) {
        loginCode.observe(owner, observer)
    }

    fun tryLogin(user: FirebaseUser?) {

    }
}