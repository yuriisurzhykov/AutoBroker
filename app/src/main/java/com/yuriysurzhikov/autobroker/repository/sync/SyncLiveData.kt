package com.yuriysurzhikov.autobroker.repository.sync

import android.util.Log
import androidx.lifecycle.LiveData
import com.yuriysurzhikov.autobroker.model.events.SyncFailedEvent
import com.yuriysurzhikov.autobroker.model.events.SyncStartEvent
import com.yuriysurzhikov.autobroker.model.events.SyncSuccessEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SyncLiveData : LiveData<SyncData>() {

    private val TAG = SyncLiveData::class.simpleName

    override fun onActive() {
        Log.e(TAG, "onActive: ${value?.isSyncing}")
        EventBus.getDefault().register(this)
    }

    override fun onInactive() {
        Log.e(TAG, "onInactive: ${value?.isSyncing}")
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSyncSuccess(event: SyncSuccessEvent) {
        Log.e(TAG, "onSyncSuccess: $event")
        postValue(SyncData(syncStatus = 0, isSyncing = false, event = event))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSyncFail(event: SyncFailedEvent) {
        Log.e(TAG, "onSyncFail: $event")
        postValue(SyncData(syncStatus = -1, isSyncing = false, event = event))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSyncStarted(event: SyncStartEvent) {
        Log.e(TAG, "onSyncStarted: $event")
        postValue(SyncData(syncStatus = 1, isSyncing = true, event = event))
    }
}