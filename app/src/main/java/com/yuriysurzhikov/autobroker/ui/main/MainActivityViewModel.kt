package com.yuriysurzhikov.autobroker.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.yuriysurzhikov.autobroker.model.events.LogoutEvent
import com.yuriysurzhikov.autobroker.repository.local.UserRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class MainActivityViewModel
@ViewModelInject
constructor(
    private val localDatabase: UserRepositoryImpl
) : ViewModel() {

    fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            localDatabase.logout()
            EventBus.getDefault().postSticky(LogoutEvent())
        }
    }
}