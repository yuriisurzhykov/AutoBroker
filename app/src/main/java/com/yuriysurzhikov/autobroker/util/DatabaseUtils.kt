package com.yuriysurzhikov.autobroker.util

import java.util.*

object DatabaseUtils {

    fun generateRandomUserName(): String {
        return UUID.randomUUID().toString()
    }
}