package com.yuriysurzhikov.autobroker.model.entity

data class UserLocation(
    var city: String,
    var region: String
) {
    companion object {
        fun empty(): UserLocation {
            return UserLocation("", "")
        }
    }
}