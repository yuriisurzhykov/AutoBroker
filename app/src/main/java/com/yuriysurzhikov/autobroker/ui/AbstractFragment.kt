package com.yuriysurzhikov.autobroker.ui

import androidx.fragment.app.Fragment
import com.yuriysurzhikov.autobroker.model.events.SyncSuccessEvent
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
abstract class AbstractFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onSyncComplete(event: SyncSuccessEvent) {
    }
}