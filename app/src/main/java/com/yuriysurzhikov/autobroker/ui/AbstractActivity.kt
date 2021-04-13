package com.yuriysurzhikov.autobroker.ui

import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.yuriysurzhikov.autobroker.model.events.SyncFailedEvent
import com.yuriysurzhikov.autobroker.model.events.SyncStartEvent
import com.yuriysurzhikov.autobroker.model.events.SyncSuccessEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class AbstractActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @CallSuper
    open fun onSyncStarted(event: SyncStartEvent) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @CallSuper
    open fun onSyncSuccess(event: SyncSuccessEvent) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @CallSuper
    open fun onSyncFailed(event: SyncFailedEvent) {
    }
}