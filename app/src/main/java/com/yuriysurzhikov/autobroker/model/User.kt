package com.yuriysurzhikov.autobroker.model

data class User(
    var name: String?,
    var displayName: String?,
    var phone: String?,
    var email: String?,
    var location: UserLocation
)