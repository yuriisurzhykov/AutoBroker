package com.yuriysurzhikov.autobroker.repository

object ErrorCode {
    const val ERROR_UNKNOWN = -100
    const val ERROR_FAILED_LOGIN = -5
    const val ERROR_ON_BOARDING_NEEDED = -4
    const val ERROR_REGION_REQUIRED = -3
    const val ERROR_NO_SUCH_USER = -2
    const val ERROR_USER_EXISTS = -1
    const val OK = 0
    const val LOGIN_NEEDED = 1
}