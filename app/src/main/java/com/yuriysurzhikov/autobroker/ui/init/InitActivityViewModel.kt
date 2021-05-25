package com.yuriysurzhikov.autobroker.ui.init

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.repository.ErrorCode
import com.yuriysurzhikov.autobroker.repository.local.UserRepositoryImpl
import com.yuriysurzhikov.autobroker.repository.remote.UserFirebaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InitActivityViewModel @ViewModelInject constructor(
    private val localDatabase: UserRepositoryImpl,
    private val firebaseRepository: UserFirebaseRepository
) : ViewModel() {

    private val mUserLoggedStatus = MutableLiveData<Int>()
    private val firebaseAuth = FirebaseAuth.getInstance()
    var user: User? = null

    fun setLoggingObserver(owner: LifecycleOwner, observer: Observer<Int>) {
        mUserLoggedStatus.observe(owner, observer)
    }

    fun checkUserSignIn() {
        CoroutineScope(Dispatchers.IO).launch {
            user = localDatabase.getMainUser()
            val remoteUser = firebaseRepository.getUser(user?.strId)
            val resultCode =
                if (user == null) ErrorCode.ERROR_NO_SUCH_USER
                else if (remoteUser == null) ErrorCode.ERROR_NO_SUCH_USER
                else if (!user!!.fullRegistration) ErrorCode.ERROR_ON_BOARDING_NEEDED
                else if (!user!!.isLoggedIn) ErrorCode.LOGIN_NEEDED
                else ErrorCode.OK
            if (resultCode == ErrorCode.ERROR_NO_SUCH_USER && user != null) {
                localDatabase.deleteUser(user!!)
            }
            mUserLoggedStatus.postValue(resultCode)
        }
    }
}