package com.yuriysurzhikov.autobroker

import android.net.ConnectivityManager
import android.net.Network

object ConnectivityLiveData: ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
    }
}