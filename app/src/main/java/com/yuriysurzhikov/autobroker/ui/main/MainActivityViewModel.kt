package com.yuriysurzhikov.autobroker.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuriysurzhikov.autobroker.model.events.LogoutEvent
import com.yuriysurzhikov.autobroker.model.local.CarWithModelsRoom
import com.yuriysurzhikov.autobroker.repository.database.SyncDatabase
import com.yuriysurzhikov.autobroker.repository.local.UserRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class MainActivityViewModel
@ViewModelInject
constructor(
    private val localDatabase: UserRepositoryImpl,
    private val localDB: SyncDatabase
) : ViewModel() {

    private val mutableList = MutableLiveData<List<CarWithModelsRoom>>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val carWithModel = localDB.getCarsDao().getCarBrandWithModels()
            mutableList.postValue(carWithModel)
        }
    }

    fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            localDatabase.logout(true)
            EventBus.getDefault().postSticky(LogoutEvent())
        }
    }
}