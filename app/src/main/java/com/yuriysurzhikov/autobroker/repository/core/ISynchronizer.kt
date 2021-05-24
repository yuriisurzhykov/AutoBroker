package com.yuriysurzhikov.autobroker.repository.core

interface ISynchronizer {
    fun performSync(userId: String? = null)
    fun cancelSync()
    fun syncOnlyUserData()
    fun syncOnlyCars()
}