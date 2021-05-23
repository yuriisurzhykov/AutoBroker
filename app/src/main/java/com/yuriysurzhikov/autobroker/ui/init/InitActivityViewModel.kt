package com.yuriysurzhikov.autobroker.ui.init

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yuriysurzhikov.autobroker.repository.ErrorCode
import com.yuriysurzhikov.autobroker.repository.database.LocalDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InitActivityViewModel @ViewModelInject constructor(
    private val localDatabase: LocalDatabase
) : ViewModel() {

    private val mUserLoggedStatus = MutableLiveData<Int>()
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun setLoggingObserver(owner: LifecycleOwner, observer: Observer<Int>) {
        mUserLoggedStatus.observe(owner, observer)
    }

    fun checkUserSignIn() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = localDatabase.getUserRepository().getFirstUser()
            val resultCode =
                if (user == null) ErrorCode.ERROR_NO_SUCH_USER
                else if (!user.fullRegistration) ErrorCode.ERROR_ON_BOARDING_NEEDED
                else if (!user.isLogged) ErrorCode.LOGIN_NEEDED
                else ErrorCode.OK
            mUserLoggedStatus.postValue(resultCode)
        }
    }
}