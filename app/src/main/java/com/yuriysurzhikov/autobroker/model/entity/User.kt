package com.yuriysurzhikov.autobroker.model.entity

data class User(
    var name: String?,
    var displayName: String?,
    var phone: String?,
    var email: String?,
    var location: UserLocation
)