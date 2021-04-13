package com.yuriysurzhikov.autobroker.repository.sync

import android.content.Context
import android.os.Bundle
import com.yuriysurzhikov.autobroker.model.events.SyncStartEvent
import com.yuriysurzhikov.autobroker.model.events.SyncSuccessEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import java.lang.Thread.sleep

class SyncAdapter constructor(private val context: Context) {

    fun performSync(bundle: Bundle?) {
        CoroutineScope(Dispatchers.IO).launch {
            EventBus.getDefault().post(SyncStartEvent())
            sleep(1500)
            EventBus.getDefault().post(SyncSuccessEvent())
        }
    }

    companion object {
        const val ARG_USER_ID = "user-id-arguments"
    }
}