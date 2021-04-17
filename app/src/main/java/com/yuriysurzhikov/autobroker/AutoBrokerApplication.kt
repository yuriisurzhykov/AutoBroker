package com.yuriysurzhikov.autobroker

import android.app.Application
import com.yuriysurzhikov.autobroker.repository.core.ISynchronizer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AutoBrokerApplication : Application() {

    @Inject
    lateinit var sSynchronizer: ISynchronizer

    override fun onCreate() {
        super.onCreate()
        sync(sSynchronizer)
    }

    companion object {
        fun sync(synchronizer: ISynchronizer) {
            synchronizer.performSync()
        }
    }
}