package com.yuriysurzhikov.autobroker.repository.sync

data class SyncData (
    val syncStatus: Int,
    val isSyncing: Boolean,
    val event: Any
)