package com.yuriysurzhikov.autobroker

import android.app.Application
import android.content.Context
import android.os.Bundle
import com.yuriysurzhikov.autobroker.repository.sync.SyncAdapter
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AutoBrokerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        sync(this, null)
    }

    companion object {

        @JvmStatic
        fun sync(context: Context, bundle: Bundle?) {
            SyncAdapter(context).performSync(bundle)
        }
    }
}