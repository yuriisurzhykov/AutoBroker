package com.yuriysurzhikov.autobroker

import android.app.Application
import com.yuriysurzhikov.autobroker.model.events.SyncSuccessEvent
import com.yuriysurzhikov.autobroker.repository.core.ISynchronizer
import com.yuriysurzhikov.autobroker.repository.sync.ConnectivityLiveData
import com.yuriysurzhikov.autobroker.repository.sync.SyncLiveData
import com.yuriysurzhikov.autobroker.util.DataUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AutoBrokerApplication : Application() {

    @Inject
    lateinit var sSynchronizer: ISynchronizer

    override fun onCreate() {
        super.onCreate()
        ConnectivityLiveData.init(this)
        if (DataUtils.isFirstOpening(applicationContext)) {
            sync(sSynchronizer)
        } else {
            syncLiveData.onSyncSuccess(SyncSuccessEvent())
        }
    }

    fun sync() {
        sync(sSynchronizer)
    }

    fun syncUser(userId: String) {
        sync(sSynchronizer, userId)
    }

    companion object {
        val syncLiveData = SyncLiveData.instance
        fun sync(synchronizer: ISynchronizer, userId: String? = null) {
            synchronizer.performSync(userId)
        }
    }
}