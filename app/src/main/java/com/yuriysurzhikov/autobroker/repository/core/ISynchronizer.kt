package com.yuriysurzhikov.autobroker.repository.core

interface ISynchronizer {
    fun performSync()
    fun cancelSync()
}