package com.yuriysurzhikov.autobroker.repository.utils

object StringUtils {

    fun Any.toString(): String {
        return if (this is String) this else ""
    }
}