package com.yuriysurzhikov.autobroker.repository.core

interface ISyncronizer {
    fun performSync()
    fun cancelSync()
}