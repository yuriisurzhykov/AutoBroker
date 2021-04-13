package com.yuriysurzhikov.autobroker.util

sealed class Result<T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>()
}