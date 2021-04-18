package com.yuriysurzhikov.autobroker.ui

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.model.events.SyncFailedEvent
import com.yuriysurzhikov.autobroker.model.events.SyncStartEvent
import com.yuriysurzhikov.autobroker.model.events.SyncSuccessEvent
import com.yuriysurzhikov.autobroker.util.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
abstract class AbstractActivity : AppCompatActivity() {

    private lateinit var syncView: View
    private lateinit var noNetwork: View

    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract fun onCreated(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abstract)
        syncView = findViewById(R.id.synchronization)
        noNetwork = findViewById(R.id.no_network)
        val contentStub: ViewStub = findViewById(R.id.content_stub)
        contentStub.layoutResource = getLayoutRes()
        contentStub.setOnInflateListener { _, _ ->
            onCreated(savedInstanceState)
        }
        contentStub.inflate()
    }

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
        ViewUtils.setVisible(syncView)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @CallSuper
    open fun onSyncSuccess(event: SyncSuccessEvent) {
        ViewUtils.setGone(syncView)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @CallSuper
    open fun onSyncFailed(event: SyncFailedEvent) {
        ViewUtils.setGone(syncView)
    }
}