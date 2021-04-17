package com.yuriysurzhikov.autobroker.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.databinding.ActivityAbstractBinding
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

    private lateinit var binding: ActivityAbstractBinding
    private lateinit var syncView: View
    private lateinit var noNetwork: View

    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract fun onCreated(savedInstanceState: Bundle?)

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_abstract)
        syncView = binding.statusBar.findViewById<View>(R.id.synchronization)
        noNetwork = binding.statusBar.findViewById<View>(R.id.no_network)
        if (!binding.contentStub.isInflated) {
            binding.contentStub.viewStub?.inflatedId = getLayoutRes()
            binding.contentStub.setOnInflateListener { _, _ ->
                onCreated(savedInstanceState)
            }
            binding.contentStub.viewStub?.inflate()
        }
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