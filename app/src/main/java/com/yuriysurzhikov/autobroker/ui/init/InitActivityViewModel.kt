package com.yuriysurzhikov.autobroker.ui.init

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InitActivityViewModel: ViewModel() {

    private val mUserLoggedIn = MutableLiveData<Boolean>()

    init {
        checkUserSignIn()
    }

    fun setLoggingObserver(owner: LifecycleOwner, observer: Observer<Boolean>) {
        mUserLoggedIn.observe(owner, observer)
    }

    private fun checkUserSignIn() {
        CoroutineScope(Dispatchers.IO).launch {
            mUserLoggedIn.postValue(FirebaseAuth.getInstance().currentUser != null)
        }
    }
}