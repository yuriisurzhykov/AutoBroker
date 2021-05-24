package com.yuriysurzhikov.autobroker.util

fun String?.isNotNullOrEmpty(): Boolean {
    return this.isNullOrEmpty().not()
}

fun String?.isLettersOnly(): Boolean {
    return this?.all { it.isLetter() } ?: false
}