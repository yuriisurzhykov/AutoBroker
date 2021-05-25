package com.yuriysurzhikov.autobroker.repository.sync

data class ConnectivityData(
    val isOnline: Boolean,
    val isRoaming: Boolean,
    val isMobile: Boolean
)
