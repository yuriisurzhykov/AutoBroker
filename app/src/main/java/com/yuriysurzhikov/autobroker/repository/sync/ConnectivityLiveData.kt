package com.yuriysurzhikov.autobroker.repository.sync

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData

class ConnectivityLiveData private constructor(contextProvider: Context) :
    LiveData<ConnectivityData>() {

    private val networkCallbacks = NetworkCallback(this)
    private val connectivityManager = contextProvider
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun onActive() {
        super.onActive()
        connectivityManager.registerDefaultNetworkCallback(networkCallbacks)
    }

    override fun onInactive() {
        super.onInactive()
        update()
        connectivityManager.unregisterNetworkCallback(networkCallbacks)
    }

    private fun update() {
        val activeNetwork = connectivityManager.activeNetworkInfo
        val isOnline = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        val isMobile = (isOnline && activeNetwork?.type == ConnectivityManager.TYPE_MOBILE)
        val isRoaming = isOnline && activeNetwork?.isRoaming == true
        postValue(ConnectivityData(isOnline, isRoaming, isMobile))
    }

    private class NetworkCallback(private val liveData: ConnectivityLiveData) :
        ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            liveData.update()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            liveData.update()
        }
    }

    companion object {
        private lateinit var sInstance: ConnectivityLiveData

        fun getInstance() = sInstance

        fun init(context: Context) {
            sInstance = ConnectivityLiveData(context.applicationContext)
        }
    }
}