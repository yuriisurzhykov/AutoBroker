package com.yuriysurzhikov.autobroker.repository

sealed class Result<T> {

    object InProgress : Result<Nothing>()

    data class Success<T>(val value: T) : Result<T>()

    data class Failure<T>(val throwable: Throwable) : Result<T>()

}