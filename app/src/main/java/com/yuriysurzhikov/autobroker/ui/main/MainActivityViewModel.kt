package com.yuriysurzhikov.autobroker.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.repository.local.UserLocalRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel
@ViewModelInject
constructor(
    private val localDatabase: UserLocalRepositoryImpl
) : ViewModel() {

    private val mUser = MutableLiveData<User?>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val user = localDatabase.getMainUser()
            mUser.postValue(user)
        }
    }

    fun observeUser(owner: LifecycleOwner, observer: Observer<User?>) {
        mUser.observe(owner, observer)
    }
}