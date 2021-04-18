package com.yuriysurzhikov.autobroker.util

fun String?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}