package com.yuriysurzhikov.autobroker;

import android.app.Application;

import com.yuriysurzhikov.autobroker.repository.core.ISynchronizer;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class AutoBrokerApp extends Application {

    @Inject
    public ISynchronizer sSynchronizer;

    @Override
    public void onCreate() {
        super.onCreate();
        sync(sSynchronizer);
    }

    public static void sync(ISynchronizer sSynchronizer) {
        sSynchronizer.performSync();
    }
}
